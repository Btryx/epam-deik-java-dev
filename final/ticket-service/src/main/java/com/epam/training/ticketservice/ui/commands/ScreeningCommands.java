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
import java.util.Calendar;
import java.util.Date;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommands {

    private final ScreeningService screeningService;
    private final RoomService roomService;
    private final MovieService movieService;

    @ShellMethod(key = "create screening", value = "Create new screening")
    public String addScreening(String movieTitle, String roomName, String date) throws ParseException {

        Movie movie = movieService.getMovieByTitle(movieTitle);
        Room room = roomService.getRoomByName(roomName);
        Date convertedDate = convertStringToDate(date);

        if (movie == null){
            return "Can't create screening, because movie does not exist.";
        }
        if (room == null){
            return "Can't create screening, because room does not exist.";
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(convertedDate);
        cal.add(Calendar.MINUTE, movie.getLength());
        long endOfScreeningToBeCreated = cal.getTime().getTime();
        long startOfScreeningToBeCreated = convertedDate.getTime();
        String screeningCheck = CheckScreeningOverlapping(startOfScreeningToBeCreated, endOfScreeningToBeCreated);

        if(screeningCheck.equals("Screening created")){
            Screening screening = new Screening(movie, room, convertedDate);
            screeningService.createScreening(screening);
            return "New screening created.";
        }
        return screeningCheck;
    }

    @ShellMethod(key = "delete screening", value = "Delete existing screening")
    public String deleteScreening(String movieTitle, String roomName, String date) throws ParseException {

        Date convertedDate = convertStringToDate(date);

        Movie movie = movieService.getMovieByTitle(movieTitle);
        Room room = roomService.getRoomByName(roomName);

        Screening screening = new Screening(movie, room, convertedDate);

        if (!screeningService.getScreeningList().contains(screening)) {
            System.out.println(screeningService.getScreeningList().get(0).getMovie());
            System.out.println(screeningService.getScreeningList().get(0).getRoom());
            System.out.println(screeningService.getScreeningList().get(0).getTime());
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

    public Date convertStringToDate(String stringDate) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .parse(stringDate);
    }

    public String CheckScreeningOverlapping(long start, long end){

        for (Screening scr : screeningService.getScreeningList()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(scr.getTime());
            calendar.add(Calendar.MINUTE, scr.getMovie().getLength());
            long endOfScreening = calendar.getTime().getTime();
            long startOfScreening = scr.getTime().getTime();

            if(startOfScreening <= start &&
                    endOfScreening >= start ||
                    startOfScreening <= end &&
                            endOfScreening >= end
            ) return "There is an overlapping screening";

            calendar.add(Calendar.MINUTE, 10);
            endOfScreening = calendar.getTime().getTime();
            calendar.setTime(scr.getTime());
            calendar.add(Calendar.MINUTE, -10);
            startOfScreening = calendar.getTime().getTime();

            if(startOfScreening <= start &&
                    endOfScreening >= start ||
                    startOfScreening <= end &&
                            endOfScreening >= end
            ) return "This would start in the break period after another screening in this room.";
        }
        return "Screening created";
    }

}
