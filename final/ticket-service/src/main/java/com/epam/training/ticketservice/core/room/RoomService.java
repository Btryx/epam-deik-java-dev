package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.Room;

import java.util.List;

public interface RoomService {
    void initRooms();

    void createRoom(Room room);

    List<Room> getRoomList();

    Room getRoomByName(String name);

    void deleteRoom(Room room);

    void updateRoom(String name, int rows, int cols);

    List<String> getRoomNames();
}
