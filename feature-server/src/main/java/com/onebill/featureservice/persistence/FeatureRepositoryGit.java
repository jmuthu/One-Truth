package com.onebill.featureservice.persistence;

//import com.onebill.featureservice.representations.Feature;

import gherkin.formatter.JSONFormatter;
import gherkin.parser.Parser;

import com.google.common.base.Optional;
import com.onebill.featureservice.representations.Feature;
import com.onebill.featureservice.representations.FeatureComponent;
import com.onebill.featureservice.representations.FeatureGroup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeatureRepositoryGit {
	public final static String ROOT_ID = "root";
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FeatureRepositoryGit.class);
	private String uri;
	private String repoName;
	private Repository repository;
	private Ref head;
	private RevWalk walk;

	public FeatureRepositoryGit(String uri, String repoName) {
		this.uri = new String(uri + "/.git");
		this.repoName = repoName;
	}

	public Boolean init() {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		try {
			repository = builder.setGitDir(new File(this.uri))
					.readEnvironment() // scan
					// environment
					// GIT_*
					// variables
					.findGitDir() // scan up the file system tree
					.build();
			head = repository.getRef("refs/heads/master");
			// a RevWalk allows to walk over commits based on some filtering
			// that is
			// defined
			walk = new RevWalk(repository);
		} catch (IOException e) {
			LOGGER.info("Error opening the repository" + this.uri);
			return false;
		}
		return true;
	}

	protected void setGroupComponents(TreeWalk treeWalk,
			List<FeatureComponent> featureComponentList) throws IOException {
		try {
			while (treeWalk.next()) {
				String objectId = treeWalk.getObjectId(0).toString();
				String Id = objectId.substring(objectId.indexOf('[') + 1,
						objectId.indexOf(']'));
				if (treeWalk.isSubtree()) {
					featureComponentList.add(new FeatureGroup(
							FeatureComponent.FeatureType.GROUP, treeWalk
									.getNameString(), Id));
					// treeWalk.enterSubtree();
				} else {
					featureComponentList.add(new Feature(
							FeatureComponent.FeatureType.FEATURE, treeWalk
									.getNameString(), Id));
				}
			}
			LOGGER.info("Requested Group Contains "
					+ featureComponentList.size() + " objects");
		} catch (IOException io) {
			LOGGER.info("Error walking the repository" + io.getMessage());
			throw io;
		}
	}

	public FeatureGroup getGroupContents(Optional<String> id) {
		try {
			FeatureGroup featureGroup = new FeatureGroup();
			featureGroup.setType(FeatureComponent.FeatureType.GROUP);
			TreeWalk treeWalk = new TreeWalk(repository);
			if (id.isPresent()) {
				AnyObjectId objectId = ObjectId.fromString(id.get()); 
				treeWalk.addTree(objectId);
				featureGroup.setId(id.get());
				featureGroup.setName(id.get());
			} else {
				featureGroup.setId(ROOT_ID);
				featureGroup.setName(this.repoName);
				RevCommit commit = walk.parseCommit(head.getObjectId());
				RevTree tree = commit.getTree();
				// now use a TreeWalk to iterate over all files in the Tree
				// recursively
				// you can set Filters to narrow down the results if needed
				treeWalk.addTree(tree);
			}
			setGroupComponents(treeWalk, featureGroup.getFeatureComponentList());
			return featureGroup;
		} catch (IOException io) {
			LOGGER.info("error retrieving directory contents");
			return null;
		}
	}

	public String getFeatureContents(String id) {
		try {
			// String path =
			// "/home/joe/Documents/onebill_docs/Dashboard/BusinessDashboard.feature";
			// String gherkin = FixJava.readReader(new InputStreamReader(
			// new FileInputStream(path), "UTF-8"));

			ObjectLoader loader = repository.open(ObjectId.fromString(id));
			StringBuilder json = new StringBuilder();

			JSONFormatter formatter = new JSONFormatter(json);
			Parser parser = new Parser(formatter);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			loader.copyTo(os);
			String aString = new String(os.toByteArray(), "UTF-8");
			// LOGGER.info("file : " + aString);
			parser.parse(aString, uri, 0);
			formatter.done();
			formatter.close();
			// LOGGER.info("json: '" + json + "'"); // Gherkin source as
			// Gson gson = new Gson();
			// Feature feature = gson.fromJson(json.toString(), Feature.class);
			return json.toString();
		} catch (IOException io) {
			LOGGER.info("error" + io.getMessage());
		}
		return null;
	}

	public List<Feature> search(String query) {
		return null;
	}
}
