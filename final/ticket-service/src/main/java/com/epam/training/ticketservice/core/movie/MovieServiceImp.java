package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.Movie;

import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MovieServiceImp implements MovieService{

    private List<Movie> movieList;

    @Override
    public void initMovies() {
        movieList = new LinkedList<>(List.of(
                Movie.builder()
                        .withName("Lord of The Rings")
                        .withGenre("Fantasy")
                        .withLength(167)
                        .build(),
                Movie.builder()
                        .withName("IT")
                        .withGenre("Horror")
                        .withLength(125)
                        .build(),
                Movie.builder()
                        .withName("Tangled")
                        .withGenre("Animation")
                        .withLength(167)
                        .build()));
    }

    @Override
    public void createMovie(Movie movie) {
        movieList.add(movie);
    }

    @Override
    public List<Movie> getMovieList() {
        return movieList;
    }

    @Override
    public void deleteMovie(Movie movie) {
        movieList.remove(movie);
    }

    @Override
    public void updateMovie(Movie movie, String name, String genre, int length) {
        Movie newMovie = new Movie(name, genre, length);
        movieList.remove(movie);
        movieList.add(newMovie);
    }
}
