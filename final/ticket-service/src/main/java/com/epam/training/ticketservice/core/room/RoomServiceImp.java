package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImp implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public void createRoom(RoomDto roomDto) {
        Room room = new Room(roomDto.getName(), roomDto.getRows(), roomDto.getCols());
        roomRepository.save(room);
    }

    @Override
    public List<RoomDto> getRoomList() {
        return roomRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomByName(String name) {
        return convertEntityToDto(roomRepository.getById(name));
    }

    @Override
    public void deleteRoom(RoomDto roomDto) {
        Room room = new Room(roomDto.getName(), roomDto.getRows(), roomDto.getCols());
        roomRepository.delete(room);
    }

    @Override
    public void updateRoom(String name, int rows, int cols) {
        RoomDto room = getRoomByName(name);
        RoomDto newRoom = new RoomDto(name, rows, cols);
        deleteRoom(room);
        createRoom(newRoom);
    }

    private RoomDto convertEntityToDto(Room room) {
        return RoomDto.builder()
                .withName(room.getName())
                .withRows(room.getRows())
                .withCols(room.getCols())
                .build();
    }
}
