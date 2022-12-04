package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImp implements MovieService {

    @Autowired
    private MovieRepository movieRepository;


    @Override
    public void createMovie(MovieDto movieDto) {
        if (existsByTitle(movieDto.getTitle())) {
            throw new IllegalArgumentException("Movie with this title already exists");
        }
        Movie movie = new Movie(movieDto.getTitle(), movieDto.getGenre(), movieDto.getLength());
        movieRepository.save(movie);
    }

    @Override
    public List<MovieDto> getMovieList() {
        return movieRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MovieDto> getMovieByTitle(String title) {
        return Optional.ofNullable(convertEntityToDto(movieRepository.findByTitle(title)));
    }

    @Override
    @Transactional
    public void deleteMovieByTitle(String title) {
        if (!existsByTitle(title)) {
            throw new IllegalArgumentException("Movie does not exist");
        }
        movieRepository.deleteMovieByTitle(title);
    }

    @Override
    public Movie findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Override
    @Transactional
    public void updateMovie(String title, String genre, int length) {

        if (!existsByTitle(title)) {
            throw new IllegalArgumentException("Movie does not exist");
        }

        Movie movie = movieRepository.findByTitle(title);
        movie.setGenre(genre);
        movie.setLength(length);
        movieRepository.save(movie);
    }

    @Override
    public Boolean existsByTitle(String title) {
        return movieRepository.existsByTitle(title);
    }


    private MovieDto convertEntityToDto(Movie movie) {
        return MovieDto.builder()
                .withTitle(movie.getTitle())
                .withGenre(movie.getGenre())
                .withLength(movie.getLength())
                .build();
    }
}
