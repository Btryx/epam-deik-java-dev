package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void deleteRoom(RoomDto roomDto) {
        Room room = roomRepository.findByName(roomDto.getName());
        roomRepository.delete(room);
    }

    @Override
    public void updateRoom(String name, int rows, int cols) {
        Optional<RoomDto> room = getRoomByRoomName(name);
        if (room.isPresent()) {
            RoomDto r = new RoomDto(room.get().getName(),
                    room.get().getRows(),
                    room.get().getCols());
            deleteRoom(r);
            RoomDto newRoom = new RoomDto(name, rows, cols);
            createRoom(newRoom);
        }
    }

    private RoomDto convertEntityToDto(Room room) {
        return RoomDto.builder()
                .withName(room.getName())
                .withRows(room.getRows())
                .withCols(room.getCols())
                .build();
    }
}
