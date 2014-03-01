package com.onebill.featureservice.health;

import com.onebill.featureservice.persistence.FeatureRepositoryGit;
import com.yammer.metrics.core.HealthCheck;

public class FeatureServiceHealthCheck extends HealthCheck {
    private final String url;

    public FeatureServiceHealthCheck(String url) {
        super("GIT REPO CHECK");
        this.url = url;
    }

    @Override
    protected Result check() throws Exception {
        // the following template string is expected: Hello, %s!
    	FeatureRepositoryGit repo = new FeatureRepositoryGit(this.url,
				"GIT REPO CHECK");
		if (!repo.init()) {
			return Result.unhealthy("Unable to read repositories");
		}
        return Result.healthy();
    }
}
