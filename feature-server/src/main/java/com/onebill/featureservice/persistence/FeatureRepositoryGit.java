package com.onebill.featureservice.persistence;

import gherkin.formatter.JSONFormatter;
import gherkin.parser.Parser;

import com.google.common.base.Optional;
import com.onebill.featureservice.representations.Feature;
import com.onebill.featureservice.representations.FeatureComponent;
import com.onebill.featureservice.representations.FeatureGroup;
import com.onebill.featureservice.representations.FeatureSearchResult;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
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

	protected TreeWalk getHeadTree() throws IOException {
		try {
			TreeWalk treeWalk = new TreeWalk(repository);
			RevCommit commit = walk.parseCommit(head.getObjectId());
			RevTree tree = commit.getTree();
			treeWalk.addTree(tree);
			return treeWalk;
		} catch (IOException ex) {
			throw ex;
		}
	}

	protected void walkTreeToPath(TreeWalk treeWalk, String path)
			throws IOException {
		try {
			String[] components = path.split("/");
			// Walk the tree till you find the object in the path
			for (int i = 0; i < components.length && treeWalk.next();) {
				// LOGGER.info(treeWalk.getNameString() + "==" + components[i]);
				if (treeWalk.getNameString().equals(components[i])) {
					// Only Enter subtree if the object is a tree and
					// if it is not the final part of the path
					if (treeWalk.isSubtree() && components.length - 1 != i) {
						treeWalk.enterSubtree();
					}
					i++;
				}

			}
		} catch (IOException ex) {
			throw ex;
		}
	}

	public String getActualPath(String path) {
		String[] pathElements = path.split("/", 2);
		// parse out root directory
		String actualPath = null;
		if (pathElements.length == 2) {
			actualPath = pathElements[1];
			LOGGER.info("Actual Path :" + actualPath);
		}
		return actualPath;
	}

	public FeatureGroup getGroupContentsFromPath(String path) {
		try {
			TreeWalk treeWalk = getHeadTree();
			FeatureGroup featureGroup = new FeatureGroup();
			featureGroup.setType(FeatureComponent.FeatureType.GROUP);
			featureGroup.setName(this.repoName);

			String actualPath = getActualPath(path);
			if (actualPath != null) {
				walkTreeToPath(treeWalk, actualPath);
				featureGroup.setName(treeWalk.getNameString());
				featureGroup.setId(treeWalk.getObjectId(0).getName());
				treeWalk.enterSubtree();
			}

			// Get the contents of the requested group

			while (treeWalk.next()) {
				if (actualPath != null
						&& treeWalk.isPathPrefix(actualPath.getBytes(),
								actualPath.length() - 1) > 0) {
					// Break out parsing after the requested group's content is
					// traversed
					break;
				}
				String name = treeWalk.getNameString();
				String id = treeWalk.getObjectId(0).getName();
				String childPath = path + "/" + name;
				if (treeWalk.isSubtree()) {
					featureGroup.getFeatureComponentList().add(
							new FeatureGroup(name, id, childPath));
				} else {
					featureGroup.getFeatureComponentList().add(
							new Feature(name, id, childPath));
				}
			}
			return featureGroup;
		} catch (IOException io) {
			LOGGER.info("error retrieving directory contents");
			return null;
		}
	}

	public String getFeatureContentsFromPath(String path) {
		try {
			TreeWalk treeWalk = getHeadTree();
			String actualPath = getActualPath(path);
			if (actualPath != null) {
				walkTreeToPath(treeWalk, actualPath);
			}
			return getFeatureContents(treeWalk.getObjectId(0).getName());
		} catch (IOException io) {
			LOGGER.info("error" + io.getMessage());
		}
		return null;
	}

	public String getFeatureContents(String id) {
		try {
			ObjectId objectId = ObjectId.fromString(id);
			ObjectLoader loader = repository.open(objectId);
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
			// LOGGER.info(json.toString());
			// Feature feature = new Feature(objectId.getName(),
			// objectId.getName(), json.toString());
			return json.toString();
		} catch (IOException io) {
			LOGGER.info("error" + io.getMessage());
		}
		return null;
	}

	public List<FeatureSearchResult> query(String query) {
		try {
			ObjectReader objectReader = repository.newObjectReader();
			List<FeatureSearchResult> result = null;
			try {
				result = impl(objectReader, query);
			} finally {
				objectReader.release();
			}
			return result;
		} catch (IOException io) {
			LOGGER.info("error" + io.getMessage());
		}
		return null;
	}

	private List<FeatureSearchResult> impl(ObjectReader objectReader,
			String query) throws IOException {
		Pattern pattern = Pattern.compile(query);
		TreeWalk treeWalk = getHeadTree();
		
		List<FeatureSearchResult> featureSearchResults = Collections
				.synchronizedList(new ArrayList<FeatureSearchResult>());

		treeWalk.setRecursive(true);

		while (treeWalk.next()) {
			AbstractTreeIterator it = treeWalk.getTree(0,
					AbstractTreeIterator.class);
			ObjectLoader objectLoader = objectReader.open(treeWalk.getObjectId(0));

			if (!isBinary(objectLoader.openStream())) {
				List<String> matchedLines = getMatchedLines(objectLoader
						.openStream(), pattern);
				if (!matchedLines.isEmpty()) {
					String path = "OneBill/" + it.getEntryPathString();
					featureSearchResults.add(new FeatureSearchResult(path,
							treeWalk.getObjectId(0).getName(), matchedLines));
					/*
					 * for (String matchedLine : matchedLines) {
					 * featureSearchResults.add(new FeatureSearchResult(path,
					 * objectId.toString(), matchedLine)); System.out
					 * .println(path + ":" + objectId.getName() + "," +
					 * objectId.toString() + ":" + matchedLine); }
					 */
				}
			}
		}
		return featureSearchResults;
	}

	private List<String> getMatchedLines(InputStream stream, Pattern pattern) throws IOException {
		BufferedReader buf = null;
		try {
			List<String> matchedLines = new ArrayList<String>();
			InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
			buf = new BufferedReader(reader);
			String line;
			while ((line = buf.readLine()) != null) {
				Matcher m = pattern.matcher(line);
				if (m.find()) {
					matchedLines.add(line);
				}
			}
			return matchedLines;
		} finally {
			if (buf != null) {
				buf.close();
			}
		}
	}

	private static boolean isBinary(InputStream stream) throws IOException {
		try {
			return RawText.isBinary(stream);
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				// Ignore, we were just reading
			}
		}
	}

	protected void setGroupComponents(TreeWalk treeWalk,
			List<FeatureComponent> featureComponentList, int treeIndex)
			throws IOException {
		try {
			while (treeWalk.next()) {
				String Id = treeWalk.getObjectId(0).getName();
				// String Id = objectId.substring(objectId.indexOf('[') + 1,
				// objectId.indexOf(']'));
				if (treeWalk.isSubtree()) {
					featureComponentList.add(new FeatureGroup(treeWalk
							.getNameString(), Id, ""));
					// treeWalk.enterSubtree();
				} else {
					featureComponentList.add(new Feature(treeWalk
							.getNameString(), Id, ""));
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
			int treeIndex = 0;
			if (id.isPresent()) {
				AnyObjectId objectId = ObjectId.fromString(id.get());
				treeIndex = treeWalk.addTree(objectId);
				AbstractTreeIterator it = treeWalk.getTree(treeIndex,
						AbstractTreeIterator.class);
				featureGroup.setId(id.get());
				featureGroup.setName(it.getEntryPathString());
			} else {
				featureGroup.setId(ROOT_ID);
				featureGroup.setName(this.repoName);
				RevCommit commit = walk.parseCommit(head.getObjectId());
				RevTree tree = commit.getTree();
				// now use a TreeWalk to iterate over all files in the Tree
				// recursively
				// you can set Filters to narrow down the results if needed
				treeIndex = treeWalk.addTree(tree);
			}
			setGroupComponents(treeWalk,
					featureGroup.getFeatureComponentList(), treeIndex);
			return featureGroup;
		} catch (IOException io) {
			LOGGER.info("error retrieving directory contents");
			return null;
		}
	}
}
