package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

public class RoomServiceImpTest {

    private static final Room ENTITY = new Room("Room1", 10, 10);
    private static final RoomDto DTO = new RoomDto.Builder()
            .withName("Room1")
            .withRows(10)
            .withCols(10)
            .build();
    private final RoomRepository RoomRepository = Mockito.mock(RoomRepository.class);
    private final RoomServiceImp underTest = new RoomServiceImp(RoomRepository);

    @Test
    void testGetRoomListShouldReturnAStaticListWithTwoElements() {
        // Given
        Mockito.when(RoomRepository.findAll()).thenReturn(List.of(ENTITY));
        List<RoomDto> expected = List.of(DTO);

        // Mockito.when
        List<RoomDto> actual = underTest.getRoomList();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(RoomRepository).findAll();
    }

    @Test
    void testGetRoomByTitleShouldReturnTitleWhenInputRoomTitleIsTitle() {
        // Given
        Mockito.when(RoomRepository.findByName("Room1")).thenReturn(ENTITY);
        Optional<RoomDto> expected = Optional.of(DTO);

        // Mockito.when
        Optional<RoomDto> actual = underTest.getRoomByRoomName("Room1");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(RoomRepository).findByName("Room1");
    }

    @Test
    void testCreateRoomShouldStoreTheGivenRoomWhenTheInputRoomIsValid() {
        // Given
        Mockito.when(RoomRepository.save(ENTITY)).thenReturn(ENTITY);

        // Mockito.when
        underTest.createRoom(DTO);

        // Then
        Mockito.verify(RoomRepository).save(ENTITY);
    }
}
