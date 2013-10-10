package com.prodyna.pac.conference.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.conference.model.Room;
import com.prodyna.pac.conference.service.RoomService;

@Model
public class RoomController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private RoomService roomService;

	private Room newRoom;

	@Produces
	@Named("newRoom")
	public Room getNewRoom() {
		return newRoom;
	}

	public void cancelEditing() {
		newRoom = null;
	}

	public void editRoom(Room room) {
		newRoom = room;
	}

	public void createRoom() throws Exception {
		try {

			if (newRoom.isNew()) {
				roomService.createRoom(newRoom);
			} else {
				roomService.updateRoom(newRoom);
			}

			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Saved!", "Room saved"));
			initNewMember();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Saving unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	@PostConstruct
	public void initNewMember() {
		newRoom = new Room();
	}

	private String getRootErrorMessage(Exception e) {
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
