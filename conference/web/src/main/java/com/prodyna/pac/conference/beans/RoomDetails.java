package com.prodyna.pac.conference.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.prodyna.pac.conference.model.Room;
import com.prodyna.pac.conference.service.RoomService;
import com.prodyna.pac.conference.service.TalkService;
import com.prodyna.pac.conference.web.constants.ViewConstants;

@ManagedBean(name = "roomDetails")
@ViewScoped
public class RoomDetails extends AbstractEditEntityMaskBean {

	@Inject
	RoomService roomService;

	@Inject
	TalkService talkService;
	@Inject
	private FacesContext facesContext;

	private Room room;

	private static final long serialVersionUID = 1L;

	public Room getRoom() throws Exception
	{
		if (room == null) {
			if (isNewMode()) {
				initNewRoom();
			} else {
				room = roomService.getRoomById(getId());
			}
		}
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
	}

	public String createRoom() throws Exception
	{

		String outcome = ViewConstants.VIEW_ROOM_EDIT;
		try {
			roomService.createRoom(room);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Saved!", "Room saved"));
			room = null;
			outcome = ViewConstants.VIEW_SPEAKER_LIST;
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Saving unsuccessful");
			facesContext.addMessage(null, m);
		}

		return outcome;
	}

	public String updateRoom() throws Exception
	{
		String outcome = ViewConstants.VIEW_ROOM_EDIT;
		try {
			roomService.updateRoom(room);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Updated!", "Room updated"));
			outcome = ViewConstants.VIEW_SPEAKER_LIST;
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Updating room unsuccessful");
			facesContext.addMessage(null, m);
		}
		return outcome;
	}

	public void initNewRoom()
	{
		room = new Room();
	}

	@Override
	public String cancelEditing()
	{
		initNewRoom();

		return ViewConstants.VIEW_ROOM_LIST;
	}

}