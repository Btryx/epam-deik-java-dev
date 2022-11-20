package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImp implements MovieService {

    @Autowired
    private MovieRepository movieRepository;


    @Override
    public void createMovie(MovieDto movieDto) {
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
    public void deleteMovie(MovieDto movieDto) {
        Movie movie = movieRepository.findByTitle(movieDto.getTitle());
        movieRepository.delete(movie);
    }

    @Override
    public void updateMovie(String title, String genre, int length) {
        Optional<MovieDto> movie = getMovieByTitle(title);
        if (movie.isPresent()) {
            MovieDto m = new MovieDto(movie.get().getTitle(),
                    movie.get().getGenre(),
                    movie.get().getLength());
            deleteMovie(m);
            MovieDto newMovieDto = new MovieDto(title, genre, length);
            createMovie(newMovieDto);
        }
    }

    private MovieDto convertEntityToDto(Movie movie) {
        return MovieDto.builder()
                .withTitle(movie.getTitle())
                .withGenre(movie.getGenre())
                .withLength(movie.getLength())
                .build();
    }
}
