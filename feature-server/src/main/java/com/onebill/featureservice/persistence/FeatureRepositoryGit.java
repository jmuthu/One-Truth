package com.onebill.featureservice.persistence;

//import com.onebill.featureservice.representations.Feature;

import gherkin.formatter.JSONFormatter;
import gherkin.parser.Parser;
import gherkin.util.FixJava;
import gherkin.formatter.model.Feature;

import com.google.gson.Gson;

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

public class FeatureRepositoryGit {
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

			RevCommit commit = walk.parseCommit(head.getObjectId());
			RevTree tree = commit.getTree();
			System.out.println("Having tree: " + tree);

			// now use a TreeWalk to iterate over all files in the Tree
			// recursively
			// you can set Filters to narrow down the results if needed
			TreeWalk treeWalk = new TreeWalk(repository);
			treeWalk.addTree(tree);
			treeWalk.setRecursive(false);
			while (treeWalk.next()) {
				if (treeWalk.isSubtree()) {
					System.out.println("dir: " + treeWalk.getPathString()
							+ treeWalk.getObjectId(0));
					treeWalk.enterSubtree();
				} else {
					System.out.println("file: " + treeWalk.getPathString()
							+ treeWalk.getObjectId(0));
				}
			}

			System.out.println("Having repository: "
					+ repository.getDirectory());

		} catch (IOException e) {
			System.out.println("Error opening the repository" + this.uri);
			return false;
		}

		return true;
	}

	public Feature getFeatureContents(String id) {
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
			String aString = new String(os.toByteArray(),"US-ASCII");
			//System.out.println("file : " + aString);
			parser.parse(aString, uri, 0);
			formatter.done();
			formatter.close();
			System.out.println("json: '" + json + "'"); // Gherkin source as
			Gson gson = new Gson();
			Feature feature = gson.fromJson(json.toString(), Feature.class);
			return feature;
		} catch (IOException io) {
			System.out.println("error");
		}
		return null;
	}

	public void store(Feature feature) {

	}

	public List<Feature> search(String query) {
		return null;
	}

	public String getDirContents(String id) {
		// TODO Auto-generated method stub
	    String result = new String();
		try {
			RevCommit commit = walk.parseCommit(head.getObjectId());
			RevTree tree = commit.getTree();
		    TreeWalk treeWalk = new TreeWalk(repository);
		    treeWalk.addTree(tree);
		    treeWalk.setRecursive(false);
		    while (treeWalk.next()) {
				if (treeWalk.isSubtree()) {
					result = result + "\ndir: " + treeWalk.getPathString()
							+ treeWalk.getObjectId(0);
				} else {
					result = result + "\nfile: " + treeWalk.getPathString()
							+ treeWalk.getObjectId(0);
				}
			}

		} catch (IOException io) {
			System.out.println("error retrieving directory contents");
		}
		return result;
	}

}
