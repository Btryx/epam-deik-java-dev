package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.booking.persistence.Booking;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.persistence.User;

import java.util.Date;
import java.util.List;

public interface BookingService {

    String createBooking(String movieTitle, String roomName, Date time, String seats, User user);

    public List<Booking> getBookingForUser(String userName);
}