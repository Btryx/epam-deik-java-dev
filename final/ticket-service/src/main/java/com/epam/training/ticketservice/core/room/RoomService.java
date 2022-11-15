package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    void createRoom(RoomDto roomDto);

    List<RoomDto> getRoomList();

    Optional<RoomDto> getRoomByRoomName(String name);

    void deleteRoom(RoomDto roomDto);

    void updateRoom(String name, int rows, int cols);

}
