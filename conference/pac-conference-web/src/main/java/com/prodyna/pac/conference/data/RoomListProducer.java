package com.prodyna.pac.conference.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.conference.room.model.Room;
import com.prodyna.pac.conference.room.service.RoomService;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@RequestScoped
public class RoomListProducer {

	@Inject
	private RoomService roomService;

	private List<Room> rooms;

	@Produces
	@Named("rooms")
	public List<Room> getRooms()
	{
		return rooms;
	}

	public void onMemberListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final Room room)
			throws Exception
	{
		retrieveAllRoomsOrderedByName();
	}

	@PostConstruct
	public void retrieveAllRoomsOrderedByName() throws Exception
	{
		rooms = roomService.getAllRooms();
	}
}
