package com.onebill.featureservice.representations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeatureGroup extends FeatureComponent {

	private List<FeatureComponent> featureComponentList = Collections
			.synchronizedList(new ArrayList<FeatureComponent>());
	
	public FeatureGroup() {
	}
	
	public FeatureGroup(FeatureType type, String name, String id) {
		super(type,name,id);
	}

	public List<FeatureComponent> getFeatureComponentList() {
		return featureComponentList;
	}
	
	public void setFeatureComponentList(List<FeatureComponent> featureComponentList) {
	   this.featureComponentList = featureComponentList;
	}
	
}
