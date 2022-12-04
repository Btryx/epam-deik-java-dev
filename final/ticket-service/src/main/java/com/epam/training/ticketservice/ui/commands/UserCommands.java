package com.epam.training.ticketservice.ui.commands;


import com.epam.training.ticketservice.core.booking.BookingService;
import com.epam.training.ticketservice.core.booking.model.BookingDto;
import com.epam.training.ticketservice.core.booking.persistence.Booking;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class UserCommands {

    private final UserService userService;
    private final BookingService bookingService;

    @ShellMethod(key = "sign out", value = "User logout")
    public String logout() {
        Optional<UserDto> user = userService.logout();
        if (user.isEmpty()) {
            return "You need to login first!";
        }
        return user.get().getUsername() + " is logged out!";
    }

    @ShellMethod(key = "sign in", value = "User login")
    public String login(String username, String password) {
        Optional<UserDto> user = userService.login(username, password);
        if (user.isEmpty() || user.get().getRole() != User.Role.USER) {
            return "Login failed due to incorrect credentials";
        }
        return user.get().getUsername() + " is successfully logged in!";
    }

    @ShellMethod(key = "sign in privileged", value = "Admin login")
    public String adminLogin(String username, String password) {
        Optional<UserDto> user = userService.login(username, password);
        if (user.isEmpty() || user.get().getRole() != User.Role.ADMIN) {
            return "Login failed due to incorrect credentials";
        }

        return "Signed in with privileged account '" + user.get().getUsername() + "'";
    }

    @ShellMethod(key = "describe account", value = "Get user information")
    public String print() {
        Optional<UserDto> user = userService.describe();
        if (user.isEmpty()) {
            return "You are not signed in";
        }
        if (user.get().getRole() == User.Role.ADMIN) {
            return "Signed in with privileged account '" + user.get().getUsername() + "'";
        }
        List<Booking> bookings = bookingService
                .getBookingForUser(user.get().getUsername());

        if (bookings == null || bookings.isEmpty()) {
            return "Signed in with account '" + user.get().getUsername() + "'\n"
                    + "You have not booked any tickets yet";
        }

        return "Signed in with account '" + user.get().getUsername() + "'\n"
                + "Your previous bookings are\n"
                + bookings.toString().replace("[", "").replace("]","");
    }

    @ShellMethod(key = "sign up", value = "User registration")
    public String registerUser(String userName, String password) {
        try {
            userService.registerUser(userName, password);
            return "Registration was successful!";
        } catch (Exception e) {
            return "Registration failed!";
        }
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        return user.isPresent() && user.get().getRole() == User.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }

    private Availability isAvailableForUser() {
        Optional<UserDto> user = userService.describe();
        return user.isPresent() && user.get().getRole() == User.Role.USER
                ? Availability.available()
                : Availability.unavailable("You are not logged in!");
    }
}
