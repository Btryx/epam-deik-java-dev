package com.epam.training.ticketservice.core.configuration;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.MovieServiceImp;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.RoomServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfiguration {

    @Bean(initMethod = "initMovies")
    public MovieService movieService() {
        return new MovieServiceImp();
    }

    @Bean(initMethod = "initRooms")
    public RoomService roomService() {
        return new RoomServiceImp();
    }

}
