package com.onebill.featureservice.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

/*import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
*/

public class Feature extends FeatureComponent {
	
	@JsonProperty
	private String contentsInJson;
	
	public Feature() {
    }

    public Feature(String name, String id) {
        super(FeatureComponent.FeatureType.FEATURE, name, id);
    }
    
    public Feature(String name, String id, String contentsInJson) {
        super(FeatureComponent.FeatureType.FEATURE, name, id);
        this.contentsInJson = contentsInJson;
    }

	public void contentsInJson(String contentsInJson) {
		this.contentsInJson = contentsInJson;
	}
	
	public String getcontentsInJson() {
		return this.contentsInJson;
	}
	/*
	@JsonProperty
    private Integer id; // PK
	@JsonProperty
    private String title;
    private String desc;
    
    public class Scenario {
       	public String Description;
    	public String steps;
   	}
    
    private List<Feature.Scenario> scenarios = Collections.synchronizedList(new ArrayList<Feature.Scenario>());
    

    
    public Feature(String title, String desc, List<Feature.Scenario> scenarios) {
        this.title = title;
        this.desc = desc;
        this.scenarios = scenarios;
    }

    public Feature(Integer id, String title, String desc, List<Scenario> scenarios) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.scenarios = scenarios;
    }

    public Integer getId() {
        return id;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getdesc() {
        return desc;
    }

    public void setdesc(String desc) {
        this.desc = desc;
    }

    public List<Feature.Scenario> getscenarios() {
        return scenarios;
    }

    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feature feature = (Feature) o;

        if (desc != null ? !desc.equals(feature.desc) : feature.desc != null) return false;
        if (id != null ? !id.equals(feature.id) : feature.id != null) return false;
        if (title != null ? !title.equals(feature.title) : feature.title != null) return false;
    //    if (scenarios != null ? !scenarios.equals(feature.scenarios) : feature.scenarios != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
      //  result = 31 * result + (scenarios != null ? scenarios.hashCode() : 0);
        return result;
    }

    public boolean isValid() {
        return title != null && desc != null;
    }
    */
}
