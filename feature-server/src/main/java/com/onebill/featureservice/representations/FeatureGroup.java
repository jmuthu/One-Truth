package com.onebill.featureservice.representations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeatureGroup extends FeatureComponent {

	@JsonProperty
	private List<FeatureComponent> featureComponentList = Collections
			.synchronizedList(new ArrayList<FeatureComponent>());

	public FeatureGroup() {
	}

	public FeatureGroup(String name, String id, String path ) {
		super(FeatureComponent.FeatureType.GROUP, name, id, path);
	}

	public List<FeatureComponent> getFeatureComponentList() {
		return featureComponentList;
	}

	public void setFeatureComponentList(
			List<FeatureComponent> featureComponentList) {
		this.featureComponentList = featureComponentList;
	}

}
