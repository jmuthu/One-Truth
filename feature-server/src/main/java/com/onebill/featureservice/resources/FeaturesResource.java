package com.onebill.featureservice.resources;

import com.yammer.dropwizard.auth.Auth;
import com.yammer.metrics.annotation.Timed;
import com.google.common.base.Optional;

import com.onebill.featureservice.representations.Feature;

import com.onebill.featureservice.persistence.FeatureRepositoryGit;
import com.onebill.featureservice.representations.FeatureGroup;
import com.onebill.featureservice.representations.FeatureSearchResult;
import com.onebill.featureservice.representations.User;

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

	private FeatureRepositoryGit featureRepo;

	public FeaturesResource(FeatureRepositoryGit repo) {
		featureRepo = repo;
	}

	@GET
	@Path("/group/root")
	@Timed
	public FeatureGroup getRoot() {
		return featureRepo.getGroupContents(Optional.<String> absent());
	}

	@GET
	@Path("/group/{id}")
	@Timed
	public FeatureGroup getGroup(@Auth User user, @PathParam("id") String id) {
		FeatureGroup ret = featureRepo.getGroupContents(Optional.of(id));
		if (ret != null) {
			return ret;
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@GET
	@Path("/feature/{id}")
	@Timed
	public String getFeature(@Auth User user, @PathParam("id") String id) {
		String result = featureRepo.getFeatureContents(id);
		if (result != null) {
			return result;
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@GET
	@Path("/feature")
	@Timed
	public List<FeatureSearchResult> query(@Auth User user,
			@QueryParam("text") String queryText) {
		List<FeatureSearchResult> result = featureRepo.query(queryText);
		if (result != null) {
			return result;
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	/*
	 * @POST
	 * 
	 * @Timed public void save(Feature Feature) { if (Feature != null &&
	 * Feature.isValid()) { if (Feature.getId() != null) {
	 * featureRepo.update(Feature); } else { featureRepo.insert(Feature); } } }
	 * 
	 * @DELETE
	 * 
	 * @Path("/{id}")
	 * 
	 * @Timed
	 * 
	 * @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
	 * MediaType.TEXT_PLAIN}) public void deleteFeature(@PathParam("id") Integer
	 * id) { /** Note: AngularJS $resource will send a DELETE request as
	 * content-type test/plain for some reason; so therefore we must add
	 * MediaType.TEXT_PLAIN here.
	 * 
	 * if (FeatureRepo.findById(id) != null) { featureRepo.deleteById(id); }
	 * else { throw new WebApplicationException(Response.Status.NOT_FOUND); } }
	 */
}
