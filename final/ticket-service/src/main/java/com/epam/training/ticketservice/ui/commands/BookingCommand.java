package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.core.booking.BookingService;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.User;
import com.epam.training.ticketservice.core.user.persistence.UserRepository;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@ShellComponent
public class BookingCommand {
    private final BookingService bookingService;

    private final UserService userService;
    private final UserRepository userRepository;

    public BookingCommand(BookingService bookingService, UserService userService, UserRepository userRepository) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @ShellMethodAvailability("isAvailableForUser")
    @ShellMethod(value = "Book for a screening", key = "book")
    public String booking(String movieTitle, String roomName, String startTime, String seats) {
        try {
            Optional<UserDto> user = userService.describe();
            User u = null;
            if (user.isPresent()) {
                u = userRepository.findByUsername(user.get().getUsername()).orElseThrow();
            }
            Date convertedDate = convertStringToDate(startTime);
            return bookingService.createBooking(movieTitle, roomName, convertedDate, seats, u);
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public Date convertStringToDate(String stringDate) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .parse(stringDate);
    }

    private Availability isAvailableForUser() {
        Optional<UserDto> user = userService.describe();
        return user.isPresent() && user.get().getRole() == User.Role.USER
                ? Availability.available()
                : Availability.unavailable("You are not logged in!");
    }
}

