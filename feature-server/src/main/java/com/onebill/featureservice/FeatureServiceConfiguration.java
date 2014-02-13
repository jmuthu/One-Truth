package com.onebill.featureservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class FeatureServiceConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String url;

    
    @NotEmpty
    @JsonProperty
    private String defaultName = "Stranger";

    public String getUrl() {
        return url;
    }

    public String getDefaultName() {
        return defaultName;
    }

}