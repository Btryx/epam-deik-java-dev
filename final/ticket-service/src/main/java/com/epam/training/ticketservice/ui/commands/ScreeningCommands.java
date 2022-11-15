package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommands {

    private final ScreeningService screeningService;
    private final RoomService roomService;
    private final MovieService movieService;
    private final UserService userService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = "Create new screening")
    public String addScreening(String movieTitle, String roomName, String date) throws ParseException {

        Optional<MovieDto> movieDto = movieService.getMovieByTitle(movieTitle);
        Optional<RoomDto> roomDto = roomService.getRoomByRoomName(roomName);
        Date convertedDate = convertStringToDate(date);

        if (movieDto.isEmpty()){
            return "Can't create screening, because movie does not exist";
        }
        if (roomDto.isEmpty()){
            return "Can't create screening, because room does not exist";
        }
        RoomDto room = new RoomDto(roomDto.get().getName(),
                    roomDto.get().getRows(),
                    roomDto.get().getCols());


        MovieDto movie = new MovieDto(movieDto.get().getTitle(),
                movieDto.get().getGenre(),
                movieDto.get().getLength());

        Calendar cal = Calendar.getInstance();
        cal.setTime(convertedDate);
        cal.add(Calendar.MINUTE, movie.getLength());
        long endOfScreeningToBeCreated = cal.getTime().getTime();
        long startOfScreeningToBeCreated = convertedDate.getTime();
        String screeningCheck =
                CheckScreeningOverlapping(startOfScreeningToBeCreated, endOfScreeningToBeCreated, room);

        if(screeningCheck.equals("Screening created")){
            ScreeningDto screeningDto = new ScreeningDto(movie, room, convertedDate);
            screeningService.createScreening(screeningDto);
            return "New screening created";
        }
        return screeningCheck;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "Delete existing screening")
    public String deleteScreening(String movieTitle, String roomName, String date) throws ParseException {

        Date convertedDate = convertStringToDate(date);

        Optional<MovieDto> movieDto = movieService.getMovieByTitle(movieTitle);
        if(movieDto.isPresent()){
            MovieDto m = new MovieDto(movieDto.get().getTitle(),
                    movieDto.get().getGenre(),
                    movieDto.get().getLength());

            Optional<RoomDto> roomDto = roomService.getRoomByRoomName(roomName);
            if(roomDto.isPresent()){
                RoomDto room = new RoomDto(roomDto.get().getName(),
                        roomDto.get().getRows(),
                        roomDto.get().getCols());
                ScreeningDto screeningDto = new ScreeningDto(m, room, convertedDate);
                if(screeningService.getScreeningList().contains(screeningDto)){
                    screeningService.deleteScreening(screeningDto);
                    return "Deleted screening";
                }
            }

        }

        return "Can't delete screening, because it does not exist";
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

    public String CheckScreeningOverlapping(long start, long end, RoomDto roomDto){

        for (ScreeningDto scr : screeningService.getScreeningList()) {
            if (scr.getRoomDto().equals(roomDto)){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(scr.getTime());
                calendar.add(Calendar.MINUTE, scr.getMovieDto().getLength());
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
                ) return "This would start in the break period after another screening in this room";
            }
            return "Screening created";
        }

        return "Screening created";
    }

}
