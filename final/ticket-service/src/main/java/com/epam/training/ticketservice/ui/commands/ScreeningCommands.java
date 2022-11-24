package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommands {

    private final ScreeningService screeningService;
    private final RoomService roomService;
    private final MovieService movieService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = "Create new screening")
    public String addScreening(String movieTitle, String roomName, String date) throws ParseException {
        try {
            Movie movie = movieService.findByTitle(movieTitle);
            Room room = roomService.findByName(roomName);
            Date convertedDate = convertStringToDate(date);
            ScreeningDto screeningDto = new ScreeningDto(movie, room, convertedDate);
            screeningService.createScreening(screeningDto);
            return "Created new Screening";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "Delete existing screening")
    public String deleteScreening(String movieTitle, String roomName, String date) throws ParseException {
        try {
            Movie movie = movieService.findByTitle(movieTitle);
            Room room = roomService.findByName(roomName);
            Date convertedDate = convertStringToDate(date);
            screeningService.deleteScreening(movie, room, convertedDate);
            return "Updated screening";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String screeningListToString(List<ScreeningDto> list) {
        return list.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(key = "list screenings", value = "List all screenings")
    public String listScreenings() {
        if (screeningService.getScreeningList().size() == 0) {
            return "There are no screenings";
        }
        return screeningListToString(screeningService.getScreeningList());
    }

    public Date convertStringToDate(String stringDate) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .parse(stringDate);
    }
}
