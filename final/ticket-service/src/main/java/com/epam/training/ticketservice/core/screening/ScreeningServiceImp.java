package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.screening.persistence.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImp implements ScreeningService {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;


    @Override
    public void createScreening(ScreeningDto screeningDto) {
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
    public void deleteScreening(MovieDto movieDto, RoomDto roomDto, Date time) {
        Movie movie = movieRepository.findByTitle(movieDto.getTitle());
        Room room = roomRepository.findByName(roomDto.getName());
        Screening screening = screeningRepository.findByMovieAndRoomAndTime(movie, room, time);
        if (getScreeningList().contains(convertEntityToDto(screening))) {
            screeningRepository.delete(screening);
        } else {
            throw new IllegalArgumentException("Screening does not exist");
        }
    }

    private ScreeningDto convertEntityToDto(Screening screening) {
        return ScreeningDto.builder()
                .withMovie(screening.getMovie())
                .withRoom(screening.getRoom())
                .withTime(screening.getTime())
                .build();
    }

}
