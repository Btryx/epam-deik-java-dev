package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ScreeningService {

    void createScreening(ScreeningDto screeningDto);

    List<ScreeningDto> getScreeningList();

    void deleteScreening(MovieDto movieDto, RoomDto roomDto, Date time);

}
