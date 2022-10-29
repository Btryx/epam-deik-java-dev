package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.Movie;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MovieServiceImp implements MovieService {

    private List<Movie> movieList;

    @Override
    public void initMovies() {
        movieList = new LinkedList<>(List.of(
                Movie.builder()
                        .withtitle("IT")
                        .withGenre("Horror")
                        .withLength(125)
                        .build(),
                Movie.builder()
                        .withtitle("Tangled")
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
    public Movie getMovieByTitle(String title) {
        return movieList.stream()
                .filter(movie -> movie.getTitle().equals(title))
                .findFirst().orElse(null);
    }

    @Override
    public void deleteMovie(Movie movie) {
        movieList.remove(movie);
    }

    @Override
    public void updateMovie(String title, String genre, int length) {
        Movie movie = getMovieByTitle(title);
        if (movie != null) {
            movieList.remove(movie);
            Movie newMovie = new Movie(title, genre, length);
            movieList.add(newMovie);
        }
    }
}
