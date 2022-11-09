package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.Movie;

import java.util.List;


public interface MovieService {

    void initMovies();

    void createMovie(Movie movie);

    List<Movie> getMovieList();

    Movie getMovieByTitle(String title);

    void deleteMovie(Movie movie);

    void updateMovie(String name, String genre, int length);
}
