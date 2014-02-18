package com.onebill.featureservice.persistence;

//import com.onebill.featureservice.representations.Feature;

import gherkin.formatter.JSONFormatter;
import gherkin.parser.Parser;
import gherkin.formatter.model.Feature;

import com.google.gson.Gson;
import com.onebill.featureservice.FeatureService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeatureRepositoryGit {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FeatureRepositoryGit.class);
	private String uri;
	private Repository repository;
	private Ref head;
	private RevWalk walk;

	public FeatureRepositoryGit() {

	}

	public Boolean init(String uri) {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		try {
			this.uri = new String(uri + "/.git");
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

	public String getRoot() {
		try {
			RevCommit commit = walk.parseCommit(head.getObjectId());
			RevTree tree = commit.getTree();
			// now use a TreeWalk to iterate over all files in the Tree
			// recursively
			// you can set Filters to narrow down the results if needed
			TreeWalk treeWalk = new TreeWalk(repository);
			treeWalk.addTree(tree);
			return getDirContents(treeWalk);
		} catch (IOException e) {
			 LOGGER.info("Error opening the repository" + this.uri);
			return null;
		}

	}

	public String getDirContents(TreeWalk treeWalk) throws IOException {
		String result = new String();

		try {
			treeWalk.setRecursive(false);
			while (treeWalk.next()) {
				if (treeWalk.isSubtree()) {
					result += "dir: " + treeWalk.getPathString() + treeWalk.getNameString()
							+ treeWalk.getObjectId(0);
					// treeWalk.enterSubtree();
				} else {
					result += "file: " + treeWalk.getPathString() + treeWalk.getNameString()
							+ treeWalk.getObjectId(0);
				}
			}
		} catch (IOException io) {
			 LOGGER.info("Error walking the repository" + io.getMessage());
			throw io;
		}
		return result;
	}

	public String getDirContents(String id) {
		try {
			TreeWalk treeWalk = new TreeWalk(repository);
			treeWalk.addTree(ObjectId.fromString(id));
			return getDirContents(treeWalk);
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
			//  LOGGER.info("file : " + aString);
			parser.parse(aString, uri, 0);
			formatter.done();
			formatter.close();
			//  LOGGER.info("json: '" + json + "'"); // Gherkin source as
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
