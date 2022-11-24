package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.screening.persistence.ScreeningRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class ScreeningServiceImpTest {

    private ScreeningServiceImp underTest;
    private ScreeningRepository screeningRepository;
    private RoomRepository roomRepository;
    private MovieRepository movieRepository;

    private static final Movie MOVIE = new Movie("title", "genre", 123);
    private static final Room ROOM = new Room("name", 10, 10);
    private static final Date DATE = convertToDateViaInstant(LocalDateTime.of(2000,1,1,11,11));
    private static final Date DATE2 = convertToDateViaInstant(LocalDateTime.of(2000,1,1,12,11));

    private static final ScreeningDto DTO = ScreeningDto.builder()
            .withMovie(MOVIE)
            .withRoom(ROOM)
            .withTime(DATE)
            .build();

    private static final ScreeningDto DTO2 = ScreeningDto.builder()
            .withMovie(MOVIE)
            .withRoom(ROOM)
            .withTime(DATE2)
            .build();

    Screening ENTITY = new Screening(MOVIE, ROOM, DATE);
    Screening ENTITY2 = new Screening(MOVIE, ROOM, DATE2);

    private static final Integer ROWS = 10;
    private static final Integer COLUMNS = 10;

    private static final ScreeningDto SCREENING = ScreeningDto.builder()
            .withMovie(MOVIE)
            .withRoom(ROOM)
            .withTime(DATE)
            .build();

    @BeforeEach
    public void init() {
        screeningRepository = Mockito.mock(ScreeningRepository.class);
        roomRepository = Mockito.mock(RoomRepository.class);
        movieRepository = Mockito.mock(MovieRepository.class);
        underTest = new ScreeningServiceImp(screeningRepository,
                movieRepository,
                roomRepository);
        movieRepository.save(MOVIE);
        roomRepository.save(ROOM);
    }

    @Test
    void getScreeningListShouldReturnScreeningListWhenScreeningExist() {
        // Given
        List<ScreeningDto> expected = List.of(DTO);
        Mockito.when(screeningRepository.findAll()).thenReturn(List.of(ENTITY));

        // When
        List<ScreeningDto> actual = underTest.getScreeningList();

        // Then
        assertEquals(expected, actual);
        Mockito.verify(screeningRepository).findAll();

    }

    @Test
    void createScreeningShouldCreateScreeningWhenInputIsValid() {
        //Given
        Mockito.when(movieRepository.existsByTitle(MOVIE.getTitle()))
                .thenReturn(true);
        Mockito.when(roomRepository.existsByName(ROOM.getName()))
                .thenReturn(true);

        Mockito.when(screeningRepository
                    .existsByMovieAndRoomAndTime(MOVIE, ROOM
                        , DATE)).thenReturn(false);
        Mockito.when(screeningRepository
                        .save(ENTITY))
                .thenReturn(ENTITY);

        //When
        underTest.createScreening(DTO);

        //Then
        Mockito.verify(movieRepository).existsByTitle(MOVIE.getTitle());
        Mockito.verify(roomRepository).existsByName(ROOM.getName());
        Mockito.verify(screeningRepository)
                .save(ENTITY);
    }

    @Test
    public void testCreateScreeningShouldThrowIllegalArgumentExceptionWhenMovieByTitleNotExist() {

        Mockito.when(movieRepository.existsByTitle(MOVIE.getTitle()))
                .thenReturn(false);
        Mockito.when(roomRepository.existsByName(ROOM.getName()))
                .thenReturn(true);

        Mockito.when(screeningRepository
                .existsByMovieAndRoomAndTime(MOVIE, ROOM
                        , DATE)).thenReturn(false);
        Mockito.when(screeningRepository
                        .save(ENTITY))
                .thenReturn(ENTITY);


        //Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> { underTest.createScreening(DTO);});
    }

    @Test
    public void testCreateScreeningShouldThrowIllegalArgumentExceptionWhenRoomDoesNotExist() {

        //Given
        Mockito.when(movieRepository.existsByTitle(MOVIE.getTitle()))
                .thenReturn(true);
        Mockito.when(roomRepository.existsByName(ROOM.getName()))
                .thenReturn(true);

        Mockito.when(screeningRepository
                .existsByMovieAndRoomAndTime(MOVIE, ROOM
                        , DATE)).thenReturn(false);
        Mockito.when(screeningRepository
                        .save(ENTITY))
                .thenReturn(ENTITY);

        //When
        underTest.createScreening(DTO);

        //Then
        Mockito.verify(movieRepository).existsByTitle(MOVIE.getTitle());
        Mockito.verify(roomRepository).existsByName(ROOM.getName());
        Mockito.verify(screeningRepository)
                .save(ENTITY);
    }

    @Test
    public void testCreateScreeningShouldThrowIllegalArgumentExceptionWhenScreeningAlreadyExists() {

        //Given
        Mockito.when(screeningRepository
                        .existsByMovieAndRoomAndTime(MOVIE, ROOM, DATE))
                .thenReturn(true);
        Mockito.when(movieRepository.existsByTitle(MOVIE.getTitle()))
                .thenReturn(true);
        Mockito.when(roomRepository.existsByName(ROOM.getName()))
                .thenReturn(true);

        Mockito.when(screeningRepository
                        .save(ENTITY))
                .thenReturn(ENTITY);

        //Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> { underTest.createScreening(DTO);});
    }

    @Test
    public void testCreateScreeningShouldThrowNullPointerExceptionWhenScreeningIsNull() {
        // Given

        // When
        Assertions.assertThrows(NullPointerException.class,
                () -> underTest.createScreening(null));

        // Then
        Mockito.verifyNoMoreInteractions(screeningRepository);
    }

    @Test
    void testDeleteScreeningShouldDeleteScreeningWhenInputIsValid() {
        //Given
        Mockito.when(screeningRepository
                .existsByMovieAndRoomAndTime(MOVIE, ROOM, DATE))
                .thenReturn(true);

        //When
        underTest.deleteScreening(MOVIE, ROOM, DATE);

        //Then
        Mockito.verify(screeningRepository).existsByMovieAndRoomAndTime(
                MOVIE, ROOM, DATE);
        Mockito.verify(screeningRepository)
                .deleteScreeningByMovieAndRoomAndTime(MOVIE, ROOM, DATE);
    }

    @Test
    public void testDeleteShouldThrowIllegalArgumentExceptionIfScreeningDoesNotExist() {

        //When
        Mockito.when(screeningRepository
                        .existsByMovieAndRoomAndTime(MOVIE, ROOM, DATE))
                .thenReturn(false);

        //Then
        Assertions.assertThrows(IllegalArgumentException.class
                , () -> underTest.deleteScreening(MOVIE, ROOM, DATE));
    }

    static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

}
