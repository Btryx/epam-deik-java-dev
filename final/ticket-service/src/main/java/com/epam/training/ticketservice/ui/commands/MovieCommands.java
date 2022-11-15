package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class MovieCommands {

    private final MovieService movieService;
    private final UserService userService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "Create new movie")
    public String addMovie(String title, String genre, int length) {
        movieService.createMovie(new MovieDto(title, genre, length));
        return "New movie created, with the title: " + title;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "Update existing movie")
    public String editMovie(String title, String genre, int length) {
        if (movieService.getMovieByTitle(title).isPresent()) {
            movieService.updateMovie(title, genre, length);
            return "Movie with title " + title + " updated";
        }
        return "No movie with title " + title;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "Update existing movie")
    public String deleteMovie(String title) {
        Optional<MovieDto> movie = movieService.getMovieByTitle(title);
        if (movie.isPresent()) {
            MovieDto newMovie =
                    new MovieDto(movie.get().getTitle(), movie.get().getGenre(), movie.get().getLength());
            movieService.deleteMovie(newMovie);
            return "Movie with title " + title + " deleted";
        }
        return "No movie with title " + title;
    }

    public String movieListToString(List<MovieDto> list) {
        return list.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(key = "list movies", value = "List all movies")
    public String movieList() {
        List<MovieDto> movies = movieService.getMovieList();
        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        }
        return movieListToString(movies);
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        return user.isPresent() && user.get().getRole() == User.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }
}
