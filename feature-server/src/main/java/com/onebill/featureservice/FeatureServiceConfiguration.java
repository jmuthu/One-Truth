package com.onebill.featureservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.cache.CacheBuilderSpec;
import com.yammer.dropwizard.config.Configuration;

import org.hibernate.validator.constraints.NotEmpty;

public class FeatureServiceConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String url;
    
    @JsonProperty
    private CacheBuilderSpec authenticationCachePolicy;

    
   public String getUrl() {
        return url;
    }

    
    public CacheBuilderSpec getAuthenticationCachePolicy () {
    	return authenticationCachePolicy;
    }

}