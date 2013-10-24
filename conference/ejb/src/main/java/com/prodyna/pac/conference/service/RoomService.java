package com.prodyna.pac.conference.service;

import java.util.List;

import com.prodyna.pac.conference.model.Room;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public interface RoomService {

	void createRoom(Room room) throws Exception;

	void deleteRoom(Room room) throws Exception;

	void updateRoom(Room room) throws Exception;

	Room getSpeakerById(long roomId) throws Exception;

	List<Room> getAllRooms() throws Exception;

}