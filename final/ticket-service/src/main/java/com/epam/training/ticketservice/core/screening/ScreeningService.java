package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ScreeningService {

    void createScreening(ScreeningDto screeningDto);

    List<ScreeningDto> getScreeningList();

    void deleteScreening(Movie movie, Room room, Date time);

    Screening findByMovieAndRoomAndTime(Movie movie, Room room, Date time);

    Boolean existsByMovieAndRoomAndTime(Movie movie, Room room, Date time);

}
