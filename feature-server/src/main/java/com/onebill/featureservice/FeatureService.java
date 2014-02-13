package com.onebill.featureservice;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.onebill.featureservice.persistence.FeatureRepositoryGit;
import com.onebill.featureservice.resources.FeaturesResource;
import com.onebill.featureservice.FeatureServiceConfiguration;
import com.onebill.featureservice.health.FeatureServiceHealthCheck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This main-class will be used by the start_server.sh script to start the server. It can also be
 * started up in the IDE, just remember to set the correct working directory and provide the expected
 * parameters: server dw-server.yml
 */
public class FeatureService extends Service<FeatureServiceConfiguration> {

    
    public static void main(String[] args) throws Exception {
        new FeatureService().run(args);
    }

    @Override
    public void initialize(Bootstrap<FeatureServiceConfiguration> bootstrap) {
        bootstrap.setName("feature-server"); // name must match the yaml config file
    }

    @Override
    public void run(FeatureServiceConfiguration conf, Environment env) throws ClassNotFoundException {
    	String url= conf.getUrl();
    	System.out.println(url);
    	FeatureRepositoryGit repo = new FeatureRepositoryGit();
    	repo.init(url);
    	
      //  String defaultName = conf.getDefaultName();

    	env.addResource(new FeaturesResource(repo));
        env.addHealthCheck(new FeatureServiceHealthCheck(url));
    }

}
