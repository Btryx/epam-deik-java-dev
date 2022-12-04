package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.screening.persistence.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScreeningServiceImp implements ScreeningService {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;


    @Override
    public void createScreening(ScreeningDto screeningDto) {

        if (!movieRepository.existsByTitle(screeningDto.getMovie().getTitle())) {
            throw new IllegalArgumentException("Movie does not exist");
        }

        if (!roomRepository.existsByName(screeningDto.getRoom().getName())) {
            throw new IllegalArgumentException("Room does not exist");
        }

        if (screeningRepository.existsByMovieAndRoomAndTime(
                screeningDto.getMovie(), screeningDto.getRoom(), screeningDto.getTime())) {
            throw new IllegalArgumentException("Screening already exist");
        }

        checkScreeningOverlapping(screeningDto);

        Screening screening = new Screening(screeningDto.getMovie(),
                screeningDto.getRoom(), screeningDto.getTime());
        screeningRepository.save(screening);
    }

    @Override
    public List<ScreeningDto> getScreeningList() {
        return screeningRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteScreening(Movie movie, Room room, Date time) {

        if (!screeningRepository.existsByMovieAndRoomAndTime(
                movie, room, time)) {
            throw new IllegalArgumentException("Screening does not exist");
        }
        screeningRepository.deleteScreeningByMovieAndRoomAndTime(movie, room, time);
    }

    @Override
    public Screening findByMovieAndRoomAndTime(Movie movie, Room room, Date time) {
        return screeningRepository.findByMovieAndRoomAndTime(movie, room, time);
    }

    @Override
    public Boolean existsByMovieAndRoomAndTime(Movie movie, Room room, Date time) {
        return screeningRepository.existsByMovieAndRoomAndTime(movie, room, time);
    }

    private ScreeningDto convertEntityToDto(Screening screening) {
        return ScreeningDto.builder()
                .withMovie(screening.getMovie())
                .withRoom(screening.getRoom())
                .withTime(screening.getTime())
                .build();
    }

    public void checkScreeningOverlapping(ScreeningDto screeningDto) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(screeningDto.getTime());
        cal.add(Calendar.MINUTE, screeningDto.getMovie().getLength());
        long end = cal.getTime().getTime();
        long start = screeningDto.getTime().getTime();

        for (ScreeningDto scr : getScreeningList()) {
            if (scr.getRoom().equals(screeningDto.getRoom())) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(scr.getTime());
                calendar.add(Calendar.MINUTE, scr.getMovie().getLength());
                long endOfScreening = calendar.getTime().getTime();
                long startOfScreening = scr.getTime().getTime();

                if (startOfScreening <= start
                        && endOfScreening >= start
                        || startOfScreening <= end
                        && endOfScreening >= end
                ) {
                    throw new IllegalArgumentException("There is an overlapping screening");
                }

                calendar.add(Calendar.MINUTE, 10);
                endOfScreening = calendar.getTime().getTime();
                calendar.setTime(scr.getTime());
                calendar.add(Calendar.MINUTE, -10);
                startOfScreening = calendar.getTime().getTime();

                if (startOfScreening <= start
                        && endOfScreening >= start
                        || startOfScreening <= end
                        && endOfScreening >= end
                ) {
                    throw new IllegalArgumentException(
                            "This would start in the break period after another screening in this room"
                    );
                }
            }
        }
    }
}
