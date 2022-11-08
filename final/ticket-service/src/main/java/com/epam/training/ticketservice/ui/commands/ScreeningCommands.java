package com.epam.training.ticketservice.ui.commands;


import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.Movie;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.Room;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.Screening;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommands {

    private final ScreeningService screeningService;
    private final RoomService roomService;
    private final MovieService movieService;

    @ShellMethod(key = "create screening", value = "Create new screening")
    public String addScreening(String movie, String room, String date) throws ParseException {

        Date convertedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .parse(date);

        Screening screening = new Screening(movie, room, convertedDate);

        List<String> movieTitles = movieService.getMovieListTitles();
        List<String> roomNames = roomService.getRoomNames();

        if (!movieTitles.contains(movie)) {
            return "Can't create screening, because movie does not exist.";
        }

        if (!roomNames.contains(room)) {
            return "Can't create screening, because room does not exist.";
        }

        //TODO check if date is correct

        screeningService.createScreening(screening);
        return "New screening created.";
    }

    @ShellMethod(key = "delete screening", value = "Delete existing screening")
    public String deleteScreening(String movie, String room, String date) throws ParseException {

        Date convertedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .parse(date);

        Screening screening = new Screening(movie, room, convertedDate);

        if (!screeningService.getScreeningList().contains(screening)) {
            return "Can't delete screening, because it does not exists.";
        }

        screeningService.deleteScreening(screening);

        return "Deleted screening.";
    }

    @ShellMethod(key = "list screenings", value = "List all screenings")
    public String listScreenings() {

        if (screeningService.getScreeningList().size() == 0) {
            return "There are no screenings";
        }

        return screeningService.getScreeningList().toString();
    }

}
