package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    void createRoom(RoomDto roomDto);

    List<RoomDto> getRoomList();

    Optional<RoomDto> getRoomByRoomName(String name);

    void deleteRoom(String name);

    void updateRoom(String name, int rows, int cols);

    Boolean existsByName(String name);

    Room findByName(String name);
}
