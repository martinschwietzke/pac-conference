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

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.conference.service.ConferenceService;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the
 * conference table.
 */
@Path("/conference")
@RequestScoped
public class ConferenceResourceRESTService {

	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	ConferenceService conferenceService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Conference> listAllConferences()
	{
		try {
			return conferenceService.getAllConferences();
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Conference lookupConferenceById(@PathParam("id") long id)
	{
		try {
			Conference conference = conferenceService.getConferenceById(id);
			if (conference == null) {
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}

			return conference;
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Creates a new conference from the values provided. Performs validation,
	 * and will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createConference(Conference conference)
	{

		Response.ResponseBuilder builder = null;

		try {
			// Validates conference using bean validation
			validateConference(conference);

			conferenceService.createConference(conference);

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

	@DELETE
	@Path("/{conferenceId:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTalk(@PathParam("conferenceId") long conferenceId)
	{
		Response.ResponseBuilder builder = null;
		try {
			conferenceService.deleteConference(conferenceId);

			builder = Response.ok();
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		return builder.build();

	}

	/**
	 * <p>
	 * Validates the given Conference variable and throws validation exceptions
	 * based on the type of error. If the error is standard bean validation
	 * errors then it will throw a ConstraintValidationException with the set of
	 * the constraints violated.
	 * </p>
	 * <p>
	 * If the error is caused because conference enddate before the startdate it
	 * throws a regular validation exception so that it can be interpreted
	 * separately.
	 * </p>
	 * 
	 * @param conference
	 *            Conference to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If enddate is before startdate
	 */
	private void validateConference(Conference conference)
			throws ConstraintViolationException, ValidationException
	{
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Conference>> violations = validator
				.validate(conference);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

		// Check the uniqueness of the email address
		if (validateEndDateBeforeStartDate(conference)) {
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

	public boolean validateEndDateBeforeStartDate(Conference conference)
	{

		return conference.getEnd().compareTo(conference.getStart()) > 0;
	}
}
