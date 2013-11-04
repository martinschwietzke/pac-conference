package com.prodyna.pac.conference.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.conference.model.Conference;
import com.prodyna.pac.conference.model.Room;
import com.prodyna.pac.conference.service.RoomService;

@Model
public class RoomController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private RoomService roomService;

	private Room roomToEdit = new Room();

	public Room getEditedRoom()
	{
		return roomToEdit;
	}

	public void initNewRoom()
	{
		roomToEdit = new Room();
	}

	public void setRoom(Room room)
	{
		this.roomToEdit = room;
	}

	public void cancelEditing()
	{
		initNewRoom();
	}

	public void editRoom(long roomId) throws Exception
	{
		Room r = roomService.getRoomById(roomId);
		roomToEdit = r;
	}

	public void createRoom() throws Exception
	{
		try {
			roomService.createRoom(roomToEdit);

			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Saved!", "Room saved"));
			initNewRoom();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Saving unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	public void updateConference() throws Exception
	{
		try {
			roomService.updateRoom(roomToEdit);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Update!", "Room updated"));

		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Updating Room unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	private String getRootErrorMessage(Exception e)
	{
		// Default to general error message that registration failed.
		String errorMessage = "Saving Room failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}
}
