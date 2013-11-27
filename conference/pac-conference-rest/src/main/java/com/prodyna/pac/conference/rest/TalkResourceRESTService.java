package com.prodyna.pac.conference.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.prodyna.pac.conference.talk.api.model.Talk;
import com.prodyna.pac.conference.talk.api.service.TalkService;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the talk
 * table.
 */
@Path("/talk")
@RequestScoped
public class TalkResourceRESTService {

	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	TalkService talkService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Talk> listAllTalks()
	{
		try {
			return talkService.getAllTalks();
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Talk lookupTalkById(@PathParam("id") long id)
	{
		try {
			Talk talk = talkService.getTalkById(id);
			if (talk == null) {
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}

			return talk;
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}

	}

	@DELETE
	@Path("/{talkId:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTalk(@PathParam("talkId") long talkId)
	{
		Response.ResponseBuilder builder = null;
		try {
			talkService.deleteTalk(talkId);

			builder = Response.ok();
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		return builder.build();

	}

	/**
	 * Creates a new talk from the values provided. Performs validation, and
	 * will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTalk(Talk talk)
	{

		Response.ResponseBuilder builder = null;

		try {
			// Validates talk using bean validation
			validateTalk(talk);

			talkService.createTalk(talk);

			// Create an "ok" response
			builder = Response.ok();

		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("enddate", "Endate");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}

		return builder.build();
	}

	/**
	 * <p>
	 * Validates the given Talk variable and throws validation exceptions based
	 * on the type of error. If the error is standard bean validation errors
	 * then it will throw a ConstraintValidationException with the set of the
	 * constraints violated.
	 * </p>
	 * <p>
	 * If the error is caused because talk enddate before the startdate it
	 * throws a regular validation exception so that it can be interpreted
	 * separately.
	 * </p>
	 * 
	 * @param talk
	 *            Talk to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If enddate is before startdate
	 */
	private void validateTalk(Talk talk) throws ConstraintViolationException,
			ValidationException
	{
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Talk>> violations = validator.validate(talk);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

		// Check the uniqueness of the email address
		if (validateEndDateBeforeStartDate(talk)) {
			throw new ValidationException(
					"Enddate must be greater than start date");
		}
	}

	/**
	 * Creates a JAX-RS "Bad Request" response including a map of all violation
	 * fields, and their message. This can then be used by clients to show
	 * violations.
	 * 
	 * @param violations
	 *            A set of violations that needs to be reported
	 * @return JAX-RS response containing all violations
	 */
	private Response.ResponseBuilder createViolationResponse(
			Set<ConstraintViolation<?>> violations)
	{
		log.fine("Validation completed. violations found: " + violations.size());

		Map<String, String> responseObj = new HashMap<String, String>();

		for (ConstraintViolation<?> violation : violations) {
			responseObj.put(violation.getPropertyPath().toString(),
					violation.getMessage());
		}

		return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	}

	public boolean validateEndDateBeforeStartDate(Talk talk)
	{

		return talk.getEnd().compareTo(talk.getStart()) > 0;
	}
}
