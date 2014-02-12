package com.onebill.featureservice;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import no.kodemaker.ps.dw.eventservice.health.TemplateHealthCheck;
import no.kodemaker.ps.dw.eventservice.persistence.PersonDao;
import no.kodemaker.ps.dw.eventservice.representations.Person;
import no.kodemaker.ps.dw.eventservice.resources.HelloWorldResource;
import no.kodemaker.ps.dw.eventservice.resources.PersonsResource;
import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This main-class will be used by the start_server.sh script to start the server. It can also be
 * started up in the IDE, just remember to set the correct working directory and provide the expected
 * parameters: server dw-server.yml
 */
public class FeatureService extends Service<EventConfiguration> {

    private static List<Feature> features;


    public static void main(String[] args) throws Exception {
        new EventService().run(args);
    }

    @Override
    public void initialize(Bootstrap<EventConfiguration> bootstrap) {
        bootstrap.setName("feature-server"); // name must match the yaml config file
    }

    @Override
    public void run(EventConfiguration conf, Environment env) throws ClassNotFoundException {
      /*  String template = conf.getTemplate();
        String defaultName = conf.getDefaultName();
*/
    	env.addResource(new FeaturesResource(features));
        env.addHealthCheck(new FeatureServiceHealthCheck(template));
    }

}
