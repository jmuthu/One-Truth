package com.onebill.featureservice.auth;
import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class FeatureAuthenticator implements Authenticator<BasicCredentials, String> {
    @Override
    public Optional<String> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if ("secret".equals(credentials.getPassword())) {
            return Optional.of(new String(credentials.getUsername()));
        }
        return Optional.absent();
    }
}
