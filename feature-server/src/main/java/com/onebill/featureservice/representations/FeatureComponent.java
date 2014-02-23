package com.onebill.featureservice.representations;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
/*
 * This is base abstract class (similar to composite design patter). It represents
 * a feature or a group of feature.
 */
public abstract class FeatureComponent {
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

	public FeatureComponent() {
	}

	public FeatureComponent(FeatureType type, String name, String id) {
		this.type = type;
		this.name = name;
		this.id = id;
	}

	public FeatureType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void setType(FeatureType type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String Id) {
		this.id = Id;
	}

}
