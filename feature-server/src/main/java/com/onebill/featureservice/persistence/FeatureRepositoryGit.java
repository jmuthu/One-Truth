package com.onebill.featureservice.persistence;

import com.onebill.featureservice.representations.Feature;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;

public class FeatureRepositoryGit {
	private Git git;

	public FeatureRepositoryGit() {

	}

	public Boolean init(String url) {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		try {
			Repository repository = builder.setGitDir(new File(url+"/.git"))
					.readEnvironment() // scan
					// environment
					// GIT_*
					// variables
					.findGitDir() // scan up the file system tree
					.build();
			Ref head = repository.getRef("HEAD");
			// a RevWalk allows to walk over commits based on some filtering
			// that is
			// defined
			RevWalk walk = new RevWalk(repository);

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
					System.out.println("dir: " + treeWalk.getPathString());
					treeWalk.enterSubtree();
				} else {
					System.out.println("file: " + treeWalk.getPathString());
				}
			}

			System.out.println("Having repository: "
					+ repository.getDirectory());

		} catch (IOException e) {
			System.out.println("Error opening the repository" + url);
			return false;
		}

		return true;
	}

	public Feature get(String name) {
		return null;
	}

	public void store(Feature feature) {

	}

	public List<Feature> search(String query) {
		return null;
	}

}
