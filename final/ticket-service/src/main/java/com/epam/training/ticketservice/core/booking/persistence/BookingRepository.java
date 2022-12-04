package com.epam.training.ticketservice.core.booking.persistence;

import com.epam.training.ticketservice.core.screening.persistence.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> getBookingByScreening(Screening screening);

    List<Booking> getBookingByUser_Username(String name);
}
