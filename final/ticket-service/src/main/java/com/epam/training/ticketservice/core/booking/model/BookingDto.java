package com.epam.training.ticketservice.core.booking.model;

import com.epam.training.ticketservice.core.booking.persistence.Seat;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.persistence.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class BookingDto {

    private final ScreeningDto screeningDto;
    private final UserDto userDto;
    private final List<SeatDto> seatDto;



    @Override
    public String toString() {
        return "Seats "
                + seatDto.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(", "))
                + " on " + screeningDto.getMovie().getTitle()
                + " in room " + screeningDto.getRoom().getName()
                + " starting at " + new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .format(screeningDto.getTime())
                + " for " + 3000 + " HUF";
    }

}
