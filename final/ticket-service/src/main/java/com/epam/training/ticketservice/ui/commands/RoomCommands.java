package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Optional;


@ShellComponent
@AllArgsConstructor
public class RoomCommands {

    private final RoomService roomService;
    private final UserService userService;


    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room", value = "Create new room")
    public String addRoom(String name, int rows, int cols) {
        roomService.createRoom(new RoomDto(name, rows, cols));
        return "New room created, with the name: " + name + ".";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room", value = "Update existing room")
    public String editRoom(String name, int rows, int cols) {
        if (roomService.getRoomByName(name) != null) {
            roomService.updateRoom(name, rows, cols);
            return "Room with name " + name + " updated.";
        }
        return "No room with name " + name + ".";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete room", value = "Delete existing room")
    public String deleteRoom(String name) {
        RoomDto roomDto = roomService.getRoomByName(name);
        if (roomDto != null) {
            roomService.deleteRoom(roomDto);
            return "Room with name " + name + " deleted.";
        }
        return "No room with name " + name;
    }

    @ShellMethod(key = "list rooms", value = "List all rooms")
    public String roomList() {
        if (roomService.getRoomList().isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            return roomService.getRoomList().toString();
        }
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        return user.isPresent() && user.get().getRole() == User.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }
}
