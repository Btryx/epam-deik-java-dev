package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;

import java.util.List;
import java.util.Optional;


public interface MovieService {

    void createMovie(MovieDto movieDto);

    List<MovieDto> getMovieList();

    Optional<MovieDto> getMovieByTitle(String title);

    void deleteMovie(MovieDto movieDto);

    void updateMovie(String name, String genre, int length);
}
