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
    private final RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
    private final RoomServiceImp underTest = new RoomServiceImp(roomRepository);

    @Test
    void testGetRoomListShouldReturnAllMovies() {
        // Given
        Mockito.when(roomRepository.findAll()).thenReturn(List.of(ENTITY));
        List<RoomDto> expected = List.of(DTO);

        // Mockito.when
        List<RoomDto> actual = underTest.getRoomList();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(roomRepository).findAll();
    }

    @Test
    void testGetRoomByNameShouldReturnNameWhenInputRoomNameExists() {
        // Given
        Mockito.when(roomRepository.findByName("Room1")).thenReturn(ENTITY);
        Optional<RoomDto> expected = Optional.of(DTO);

        // Mockito.when
        Optional<RoomDto> actual = underTest.getRoomByRoomName("Room1");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(roomRepository).findByName("Room1");
    }

    @Test
    void testCreateRoomShouldStoreTheGivenRoomWhenTheInputRoomIsValid() {
        // Given
        Mockito.when(roomRepository.save(ENTITY)).thenReturn(ENTITY);

        // Mockito.when
        underTest.createRoom(DTO);

        // Then
        Mockito.verify(roomRepository).save(ENTITY);
    }

    @Test
    void testRoomShouldNotBeCreatedIfRoomAlreadyExists(){

        //Given
        RoomDto room = new RoomDto("Room", 5, 10);

        //When
        Mockito.when(roomRepository.existsByName(room.getName())).thenReturn(true);


        //Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> { underTest.createRoom(room);});
    }

    @Test
    public void testCreateRoomShouldThrowNullPointerExceptionWhenRoomIsNull() {
        // Given

        // When
        Assertions.assertThrows(NullPointerException.class,
                () -> underTest.createRoom(null));

        // Then
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    void movieShouldNotBeUpdatedIfMovieDoesNotExist(){

        //Given
        RoomDto room = new RoomDto("Room", 12, 12);

        //When
        Mockito.when(roomRepository.existsByName(room.getName())).thenReturn(false);


        //Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> { underTest.updateRoom(
                room.getName(),
                room.getRows(),
                room.getCols()
        );});
    }

    @Test
    public void testUpdateMovieShouldModifyTheEntityWhenTheInputIsValid() {
        //Given
        Room room = new Room("Room", 12, 15);
        Mockito.when(roomRepository
                .findByName("Room")).thenReturn(room);
        Mockito.when(roomRepository
                .existsByName("Room")).thenReturn(true);

        RoomDto required = new RoomDto.Builder()
                .withName("Room")
                .withRows(12)
                .withCols(15)
                .build();

        Room expected = new Room(required.getName(),
                required.getRows(),
                required.getCols());
        //When
        underTest.updateRoom(required.getName(),
                required.getRows(),
                required.getCols());

        //Then
        Assertions.assertEquals(expected, roomRepository
                .findByName("Room"));
        Mockito.verify(roomRepository)
                .existsByName("Room");
        Mockito.verify(roomRepository, Mockito.times(2))
                .findByName("Room");
        Mockito.verify(roomRepository)
                .save(expected);
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testWhenTheInputValidThenDeleteRoom() {

        //Given
        Mockito.when(roomRepository
                        .existsByName("Room"))
                .thenReturn(true);

        //When
        underTest.deleteRoom("Room");

        //Then
        Mockito.verify(roomRepository)
                .existsByName("Room");
        Mockito.verify(roomRepository)
                .deleteRoomByName("Room");

        Mockito.verifyNoMoreInteractions(roomRepository);

    }

    @Test
    public void testWhenRoomDoesNotExistThenDeleteShouldThrowIllegalArgException(){
        //Given
        Mockito.when(roomRepository
                        .existsByName("Room"))
                .thenReturn(false);

        //Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> { underTest.deleteRoom("Room");});
    }

    @Test
    public void testRoomFindByTitleShouldReturnRoomIfItExists(){
        //Given
        Mockito.when(roomRepository
                        .existsByName(ENTITY.getName()))
                .thenReturn(true);

        //When
        underTest.findByName(ENTITY.getName());

        //Then
        Mockito.verify(roomRepository).findByName(ENTITY.getName());
    }

}
