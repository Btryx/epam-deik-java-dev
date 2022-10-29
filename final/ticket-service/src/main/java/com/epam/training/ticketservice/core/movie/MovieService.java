package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    void createMovie(Movie movie);

    List<Movie> getMovieList();

    void deleteMovie(Movie movie);

    void updateMovie(Movie movie, String name, String genre, int length);

    void initMovies();
}
