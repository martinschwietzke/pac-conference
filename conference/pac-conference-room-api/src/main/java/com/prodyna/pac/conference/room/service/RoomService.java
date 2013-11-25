package com.prodyna.pac.conference.room.service;

import java.util.List;

import com.prodyna.pac.conference.room.model.Room;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public interface RoomService {

	Room createRoom(Room room) throws Exception;

	void deleteRoom(Room room) throws RoomReferencedException, Exception;

	Room updateRoom(Room room) throws Exception;

	Room getRoomById(long roomId) throws Exception;

	List<Room> getAllRooms() throws Exception;
}