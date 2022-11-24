package com.epam.training.ticketservice.core.screening.persistence;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.persistence.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {

    Screening findByMovieAndRoomAndTime(Movie movie, Room room, Date time);

    Boolean existsByMovieAndRoomAndTime(Movie movie, Room room, Date time);

    void deleteScreeningByMovieAndRoomAndTime(Movie movie, Room room, Date time);

}
