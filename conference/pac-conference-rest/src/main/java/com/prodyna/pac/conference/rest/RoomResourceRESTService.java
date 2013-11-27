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

import com.prodyna.pac.conference.room.api.model.Room;
import com.prodyna.pac.conference.room.api.service.RoomService;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the room
 * table.
 */
@Path("/room")
@RequestScoped
public class RoomResourceRESTService {

	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	RoomService roomService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Room> listAllRooms()
	{
		try {
			return roomService.getAllRooms();
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Room lookupRoomById(@PathParam("id") long id)
	{
		try {
			Room room = roomService.getRoomById(id);
			if (room == null) {
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}

			return room;
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}

	}

	@DELETE
	@Path("/{roomId:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRoom(@PathParam("roomId") long roomId)
	{
		Response.ResponseBuilder builder = null;
		try {
			roomService.deleteRoom(roomId);

			builder = Response.ok();
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		return builder.build();

	}

	/**
	 * Creates a new room from the values provided. Performs validation, and
	 * will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRoom(Room room)
	{

		Response.ResponseBuilder builder = null;

		try {
			// Validates room using bean validation
			validateRoom(room);

			roomService.createRoom(room);

			// Create an "ok" response
			builder = Response.ok();

		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

		return builder.build();
	}

	/**
	 * <p>
	 * Validates the given Room variable and throws validation exceptions based
	 * on the type of error. If the error is standard bean validation errors
	 * then it will throw a ConstraintValidationException with the set of the
	 * constraints violated.
	 * </p>
	 * <p>
	 * If the error is caused because room enddate before the startdate it
	 * throws a regular validation exception so that it can be interpreted
	 * separately.
	 * </p>
	 * 
	 * @param room
	 *            Room to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If enddate is before startdate
	 */
	private void validateRoom(Room room) throws ConstraintViolationException,
			ValidationException
	{
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Room>> violations = validator.validate(room);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
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

}
