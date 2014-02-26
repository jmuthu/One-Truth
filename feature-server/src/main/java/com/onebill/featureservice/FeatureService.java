package com.onebill.featureservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onebill.featureservice.auth.FeatureAuthenticator;
import com.onebill.featureservice.persistence.FeatureRepositoryGit;
import com.onebill.featureservice.representations.User;
import com.onebill.featureservice.resources.FeaturesResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.auth.CachingAuthenticator;
import com.yammer.dropwizard.auth.basic.BasicAuthProvider;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * This main-class will be used by the start_server.sh script to start the
 * server. It can also be started up in the IDE, just remember to set the
 * correct working directory and provide the expected parameters: server
 * dw-server.yml
 */
public class FeatureService extends Service<FeatureServiceConfiguration> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FeatureService.class);

	public static void main(String[] args) throws Exception {
		new FeatureService().run(args);
	}

	@Override
	public void initialize(Bootstrap<FeatureServiceConfiguration> bootstrap) {
		bootstrap.setName("feature-server"); // name must match the yaml config
												// file
	}

	@Override
	public void run(FeatureServiceConfiguration conf, Environment env)
			throws ClassNotFoundException {

		FeatureRepositoryGit repo = new FeatureRepositoryGit(conf.getUrl(),
				conf.getRepoName());
		if (!repo.init()) {
			LOGGER.info("Unable to read repositories");
			return;
		}

		// String defaultName = conf.getDefaultName();
		env.addProvider(new BasicAuthProvider<User>(
				CachingAuthenticator.wrap(new FeatureAuthenticator(),
						conf.getAuthenticationCachePolicy()),
				"Protected Service, credentials are required"));
		env.addResource(new FeaturesResource(repo));
//		env.addHealthCheck(new FeatureServiceHealthCheck(conf.getUrl()));
	}

}
