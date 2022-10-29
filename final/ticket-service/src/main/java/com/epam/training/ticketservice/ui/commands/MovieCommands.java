package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.Movie;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class MovieCommands {

    private final MovieService movieService;

    @ShellMethod(key = "create movie", value = "Create new movie")
    public String addMovie(String name, String genre, int length){
        movieService.createMovie(new Movie(name, genre, length));
        return "New movie created, with the title: " + name + ".";
    }

    @ShellMethod(key = "update movie", value = "Update existing movie")
    public String editMovie(String name, String genre, int length){
        if(movieService.getMovieByName(name) != null){
            movieService.updateMovie(name, genre, length);
            return "Movie with title " + name + " updated.";
        }
        return "No movie with title " + name + ".";
    }

    @ShellMethod(key = "delete movie", value = "Update existing movie")
    public String deleteMovie(String name){
        Movie movie = movieService.getMovieByName(name);
        if(movie != null){
            movieService.deleteMovie(movie);
            return "Movie with title " + name + " deleted.";
        }
        return "No movie with title " + name + ".";
    }

    @ShellMethod(key = "list movies", value = "List all movies")
    public String movieList() {
        if (movieService.getMovieList().isEmpty()) {
            return "There are no movies at the moment!";
        } else {
            return movieService.getMovieList().toString();
        }
    }
}
