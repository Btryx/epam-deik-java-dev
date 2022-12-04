package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.booking.model.BookingDto;
import com.epam.training.ticketservice.core.booking.model.SeatDto;
import com.epam.training.ticketservice.core.booking.persistence.Booking;
import com.epam.training.ticketservice.core.booking.persistence.BookingRepository;
import com.epam.training.ticketservice.core.booking.persistence.Seat;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.screening.persistence.ScreeningRepository;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.User;
import com.epam.training.ticketservice.core.user.persistence.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.epam.training.ticketservice.core.user.persistence.User.Role.USER;


public class BookingServiceImplTest {

    private BookingServiceImpl underTest;
    private ScreeningRepository screeningRepository;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private RoomRepository roomRepository;
    private MovieRepository movieRepository;
    private UserService userService;

    private static final Movie MOVIE = new Movie("title", "genre", 123);
    private static final Room ROOM = new Room("name", 10, 10);
    private static final User USER_ENTITY = new User("username", "password", USER);
    private static final UserDto USERDto = new UserDto("username", USER);
    private static final Date DATE = convertToDateViaInstant(LocalDateTime.of(2000,1,1,11,11));
    private static final Date DATE2 = convertToDateViaInstant(LocalDateTime.of(2000,1,1,12,11));

    private static final ScreeningDto SCREENING_DTO = ScreeningDto.builder()
            .withMovie(MOVIE)
            .withRoom(ROOM)
            .withTime(DATE)
            .build();

    Screening SCREENING = new Screening(MOVIE, ROOM, DATE);
    Screening SCREENING2 = new Screening(MOVIE, ROOM, DATE2);

    private static final Integer ROWS = 10;
    private static final Integer COLUMNS = 10;

    private static final ScreeningDto SCREENINGDTO = ScreeningDto.builder()
            .withMovie(MOVIE)
            .withRoom(ROOM)
            .withTime(DATE)
            .build();

    Seat seat = new Seat(5, 5);
    Seat seat2 = new Seat(2, 2);
    SeatDto seatDto = new SeatDto(5, 5);
    SeatDto seatDto2 = new SeatDto(2, 2);
    List<Seat> seatList = List.of(seat, seat2);
    List<SeatDto> seatListDto = List.of(seatDto, seatDto2);

    String seatString = "5,5 2,2";

    Booking ENTITY = new Booking(USER_ENTITY, SCREENING, seatList);
    BookingDto DTO = new BookingDto(SCREENINGDTO, USERDto, seatListDto);

    @BeforeEach
    public void init() {
        screeningRepository = Mockito.mock(ScreeningRepository.class);
        roomRepository = Mockito.mock(RoomRepository.class);
        movieRepository = Mockito.mock(MovieRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        userService = Mockito.mock(UserService.class);
        bookingRepository = Mockito.mock(BookingRepository.class);
        underTest = new BookingServiceImpl(screeningRepository,
                movieRepository,
                roomRepository,
                bookingRepository,
                userRepository,
                userService);
        movieRepository.save(MOVIE);
        roomRepository.save(ROOM);
        screeningRepository.save(SCREENING);
        userRepository.save(USER_ENTITY);
    }


    @Test
    public void testCreateBookingShouldThrowIllegalArgumentExceptionWhenScreeningDoesNotExist() {

        //Given
        Mockito.when(movieRepository.findByTitle(MOVIE.getTitle()))
                .thenReturn(MOVIE);
        Mockito.when(roomRepository.findByName(ROOM.getName()))
                .thenReturn(ROOM);
        Mockito.when(userRepository.findByUsername(USER_ENTITY.getUsername()))
                .thenReturn(Optional.of(USER_ENTITY));

        //When
        Mockito.when(screeningRepository
                .existsByMovieAndRoomAndTime(MOVIE, ROOM
                        , DATE)).thenReturn(false);

        //Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> { underTest.createBooking(
                MOVIE.getTitle(), ROOM.getName(), DATE, seatString, USER_ENTITY
        );});
    }

    @Test
    public void testCreateBookingShouldCreateBookingIfInputValid() {
        //Given
        Mockito.when(movieRepository.findByTitle(MOVIE.getTitle()))
                .thenReturn(MOVIE);
        Mockito.when(roomRepository.findByName(ROOM.getName()))
                .thenReturn(ROOM);
        Mockito.when(screeningRepository.existsByMovieAndRoomAndTime(
                        MOVIE, ROOM, DATE))
                .thenReturn(true);
        Mockito.when(userRepository.findByUsername(USERDto.getUsername()))
                .thenReturn(Optional.of(USER_ENTITY));
        Mockito.when(screeningRepository
                        .findByMovieAndRoomAndTime(
                                MOVIE, ROOM, DATE))
                .thenReturn(SCREENING);

        Mockito.when(bookingRepository.getBookingByScreening(SCREENING)).thenReturn(List.of());
        Mockito.when(bookingRepository.save(ENTITY)).thenReturn(ENTITY);

        //When
        Mockito.when(screeningRepository
                .existsByMovieAndRoomAndTime(MOVIE, ROOM
                        , DATE)).thenReturn(true);

        underTest.createBooking(
                MOVIE.getTitle(), ROOM.getName(), DATE, seatString, USER_ENTITY
        );
    }

    @Test
    public void testGetBookingForUserShouldReturnAListOfBookings(){
        //Given
        Mockito.when(bookingRepository.getBookingByUser_Username(USER_ENTITY.getUsername()))
                .thenReturn(List.of(ENTITY));

        List<Booking> expected = List.of(ENTITY);
        //When
        List<Booking> actual = underTest.getBookingForUser(USER_ENTITY.getUsername());
        //Then
        Assertions.assertEquals(expected,actual);

        Mockito.verify(bookingRepository).getBookingByUser_Username(USER_ENTITY.getUsername());
        Mockito.verifyNoMoreInteractions(bookingRepository);
    }


    static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
