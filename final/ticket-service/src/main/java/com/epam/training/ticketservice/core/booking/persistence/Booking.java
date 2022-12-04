package com.epam.training.ticketservice.core.booking.persistence;

import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.user.persistence.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Screening screening;

    @ElementCollection(fetch = FetchType.EAGER)
    List<Seat> seats;

    public Booking(User user, Screening screening, List<Seat> seats) {
        this.user = user;
        this.screening = screening;
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Seats "
                + seats.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(", "))
                + " on " + screening.getMovie().getTitle()
                + " in room " + screening.getRoom().getName()
                + " starting at " + new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .format(screening.getTime())
                + " for " + 3000 + " HUF";
    }
}
