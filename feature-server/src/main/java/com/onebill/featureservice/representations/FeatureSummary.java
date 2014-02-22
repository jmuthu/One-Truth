package com.onebill.featureservice.representations;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class FeatureSummary {
	public enum FeatureType {
		GROUP, FEATURE;

		@Override
		@JsonValue
		public String toString() {
			return super.toString().toLowerCase(Locale.ENGLISH);
		}

		@JsonCreator
		public static FeatureType parse(String type) {
			return valueOf(type.toUpperCase(Locale.ENGLISH));
		}
	}

	@JsonProperty
	private FeatureType type; /* file or dir */
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String id;

	public FeatureSummary() {
	}

	public FeatureSummary(FeatureType type, String name, String id) {
		this.type = type;
		this.name = name;
		this.id = id;
	}

	FeatureType getType() {
		return type;
	}

	String getName() {
		return name;
	}

	String getId() {
		return id;
	}

	void setType(FeatureType type) {
		this.type = type;
	}

	void setName(String name) {
		this.name = name;
	}

	void setId(String Id) {
		this.id = Id;
	}

}
