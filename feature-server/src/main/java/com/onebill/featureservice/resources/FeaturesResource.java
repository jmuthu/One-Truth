package com.onebill.featureservice.resources;

import com.yammer.metrics.annotation.Timed;
import com.onebill.featureservice.represetantions.Feature;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * The resource used to handle Feature requests.
 *
 * @author Per Spilling
 */
@Path("/features")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeaturesResource {
   

    public FeaturesResource(FeatureRepo repo) {
        FeatureRepo = repo;
    }

    @GET
    @Path("/{id}")
    @Timed
    public Feature getFeature(@PathParam("id") Integer id) {
        Feature p = FeatureRepo.findById(id);
        if (p != null) {
            return p;
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Timed
    public List<Feature> listFeatures() {
        return FeatureRepo.getAll();
    }

    @POST
    @Timed
    public void save(Feature Feature) {
        if (Feature != null && Feature.isValid()) {
            if (Feature.getId() != null) {
                FeatureRepo.update(Feature);
            } else {
                FeatureRepo.insert(Feature);
            }
        }
    }

    @DELETE
    @Path("/{id}")
    @Timed
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
    public void deleteFeature(@PathParam("id") Integer id) {
        /**
         * Note: AngularJS $resource will send a DELETE request as content-type test/plain for some reason;
         * so therefore we must add MediaType.TEXT_PLAIN here.
         */
        if (FeatureRepo.findById(id) != null) {
            FeatureRepo.deleteById(id);
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
