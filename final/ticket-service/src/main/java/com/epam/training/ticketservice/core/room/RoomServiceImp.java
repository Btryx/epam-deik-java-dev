package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImp implements RoomService {

    @Autowired
    private RoomRepository roomRepository;


    @Override
    public void createRoom(RoomDto roomDto) {
        if (existsByName(roomDto.getName())) {
            throw new IllegalArgumentException("Room already exists");
        }
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
    public Optional<RoomDto> getRoomByRoomName(String name) {
        return Optional.ofNullable(convertEntityToDto(roomRepository.findByName(name)));
    }

    @Override
    @Transactional
    public void deleteRoom(String name) {
        if (!existsByName(name)) {
            throw new IllegalArgumentException("Room does not exist");
        }
        roomRepository.deleteRoomByName(name);
    }

    @Override
    @Transactional
    public void updateRoom(String name, int rows, int cols) {
        if (!existsByName(name)) {
            throw new IllegalArgumentException("Room does not exist");
        }
        Room room = roomRepository.findByName(name);
        room.setRows(rows);
        room.setCols(cols);
        roomRepository.save(room);
    }

    @Override
    public Boolean existsByName(String name) {
        return roomRepository.existsByName(name);
    }

    @Override
    public Room findByName(String name) {
        return roomRepository.findByName(name);
    }

    private RoomDto convertEntityToDto(Room room) {
        return RoomDto.builder()
                .withName(room.getName())
                .withRows(room.getRows())
                .withCols(room.getCols())
                .build();
    }
}
