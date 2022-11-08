package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.Room;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


@ShellComponent
@AllArgsConstructor
public class RoomCommands {

    private final RoomService roomService;

    @ShellMethod(key = "create room", value = "Create new room")
    public String addRoom(String name, int rows, int cols) {
        roomService.createRoom(new Room(name, rows, cols));
        return "New room created, with the name: " + name + ".";
    }

    @ShellMethod(key = "update room", value = "Update existing room")
    public String editRoom(String name, int rows, int cols) {
        if (roomService.getRoomByName(name) != null) {
            roomService.updateRoom(name, rows, cols);
            return "Room with name " + name + " updated.";
        }
        return "No room with name " + name + ".";
    }

    @ShellMethod(key = "delete room", value = "Delete existing room")
    public String deleteRoom(String name) {
        Room room = roomService.getRoomByName(name);
        if (room != null) {
            roomService.deleteRoom(room);
            return "Room with name " + name + " deleted.";
        }
        return "No room with name " + name + ".";
    }

    @ShellMethod(key = "list rooms", value = "List all rooms")
    public String roomList() {
        if (roomService.getRoomList().isEmpty()) {
            return "There are no rooms at the moment!";
        } else {
            return roomService.getRoomList().toString();
        }
    }
}
