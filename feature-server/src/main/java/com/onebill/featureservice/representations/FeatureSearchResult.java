package com.onebill.featureservice.representations;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeatureSearchResult {
	@JsonProperty
	private String path;
	
	@JsonProperty
	private String id;
	
	@JsonProperty
	private List<String> matchedLines;
	
	public FeatureSearchResult(String path, String id, List<String> matchedLines)
	{
		this.path = path;
		this.id = id;
		this.matchedLines = matchedLines;
	}
	
	public String getPath () {
		return path;
	}
	
	public String getId () {
		return id;
	}
	
	public List<String> getMatchedLines () {
		return matchedLines;
	}
	
	public void setPath (String path) {
		this.path = path;
	}
	
	public void setId (String id) {
		this. id= id;
	}
	
	public void setMatchedLines (List<String> matchedLines){
		this.matchedLines = matchedLines;
	}
}
