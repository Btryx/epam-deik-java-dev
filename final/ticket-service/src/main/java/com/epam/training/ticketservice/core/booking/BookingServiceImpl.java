package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.booking.persistence.Booking;
import com.epam.training.ticketservice.core.booking.persistence.BookingRepository;
import com.epam.training.ticketservice.core.booking.persistence.Seat;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.screening.persistence.ScreeningRepository;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.User;
import com.epam.training.ticketservice.core.user.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private final ScreeningRepository screeningRepository;
    @Autowired
    private final MovieRepository movieRepository;
    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private final BookingRepository bookingRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserService userService;

    public BookingServiceImpl(ScreeningRepository screeningRepository,
                              MovieRepository movieRepository, RoomRepository roomRepository,
                              BookingRepository bookingRepository, UserRepository userRepository,
                              UserService userService) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public String createBooking(String movieTitle, String roomName, Date time, String seats, User user) {
        if (!screeningRepository.existsByMovieAndRoomAndTime(
                movieRepository.findByTitle(movieTitle),
                roomRepository.findByName(roomName), time)) {
            throw new IllegalArgumentException("Screening does not exist");
        }

        String seatNumbers = String.valueOf(Integer.parseInt(seats.replaceAll("[\\D]", "")));

        int[] seatList = new int[seatNumbers.length()];
        for (int i = 0; i < seatNumbers.length(); i++) {
            seatList[i] = Character.getNumericValue(seatNumbers.charAt(i));
        }

        Screening screening = screeningRepository.findByMovieAndRoomAndTime(
                movieRepository.findByTitle(movieTitle),
                roomRepository.findByName(roomName), time);

        List<Seat> seat = new ArrayList<>();
        for (int i = 0; i < seatNumbers.length(); i += 2) {
            if (seatNumbers.length() % 2 != 0) {
                throw new IllegalStateException("Invalid pairs of seat given.");
            }
            Seat seatEntity = new Seat(seatList[i], seatList[i + 1]);
            seat.add(seatEntity);
        }

        Booking booking = Booking.builder()
                .user(user)
                .screening(screening)
                .seats(seat).build();

        checkSeatExisting(booking);
        checkSeatAlreadyBooked(booking);
        bookingRepository.save(booking);

        StringBuilder formattedSeatString = new StringBuilder(seats);
        for (int i = 4 - 1; i < seats.length(); i += 4) {
            formattedSeatString.replace(i, i + 1, "), (");
        }
        return "Seats booked: (" + formattedSeatString + "); the price for this booking is 3000 HUF";
    }

    private void checkSeatExisting(Booking booking) {
        Integer seatColumns = booking
                .getScreening()
                .getRoom()
                .getCols();

        Integer seatRows = booking
                .getScreening()
                .getRoom()
                .getRows();

        booking.getSeats().forEach(seat ->
                checkSeatExistInRoom(seatColumns, seatRows, seat)
        );
    }

    private void checkSeatExistInRoom(Integer seatColumns, Integer seatRows, Seat seat) {
        if (seatRows < 0 || seatColumns < 0
                || seatRows < seat.getSeatRow() || seatColumns < seat.getSeatColumn()) {
            throw new IllegalArgumentException("Seat "
                    + seat.toString()
                    + " does not exist in this room");
        }
    }

    private void checkSeatAlreadyBooked(Booking booking) {
        List<Seat> seats = bookingRepository
                .getBookingByScreening(booking.getScreening())
                .stream()
                .map(Booking::getSeats)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        booking.getSeats().forEach(seat -> {
            if (seats.contains(seat)) {
                throw new IllegalArgumentException("Seat "
                        + seat.toString()
                        + " is already taken");
            }
        });
    }

    @Override
    public List<Booking> getBookingForUser(String userName) {
        return bookingRepository.getBookingByUser_Username(userName).stream()
                .collect(Collectors.toUnmodifiableList());
    }
}
