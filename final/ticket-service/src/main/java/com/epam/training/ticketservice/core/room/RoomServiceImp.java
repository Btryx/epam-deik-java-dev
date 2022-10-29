package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.Room;

import java.util.LinkedList;
import java.util.List;

public class RoomServiceImp implements RoomService {

    private List<Room> roomList;

    @Override
    public void initRooms() {
        roomList = new LinkedList<>(List.of(
                Room.builder()
                        .withName("2D Room")
                        .withRows(30)
                        .withCols(12)
                        .build(),
                Room.builder()
                        .withName("3D Room")
                        .withRows(30)
                        .withCols(12)
                        .build()));
    }

    @Override
    public void createRoom(Room room) {
        roomList.add(room);
    }

    @Override
    public List<Room> getRoomList() {
        return roomList;
    }

    @Override
    public Room getRoomByName(String name) {
        return roomList.stream()
                .filter(room -> room.getName().equals(name))
                .findFirst().orElse(null);
    }

    @Override
    public void deleteRoom(Room room) {
        roomList.remove(room);
    }

    @Override
    public void updateRoom(String name, int rows, int cols) {
        Room room = getRoomByName(name);
        if (room != null) {
            roomList.remove(room);
            Room newRoom = new Room(name, rows, cols);
            roomList.add(newRoom);
        }
    }
}
