package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

public class MovieServiceImpTest {

    private static final Movie ENTITY = new Movie("Kung-Fu Panda", "Animation", 100);
    private static final MovieDto DTO = new MovieDto.Builder()
            .withTitle("Kung-Fu Panda")
            .withGenre("Animation")
            .withLength(100)
            .build();
    private final MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
    private final MovieServiceImp underTest = new MovieServiceImp(movieRepository);

    @Test
    void testGetMovieListShouldReturnAStaticListWithTwoElements() {
        // Given
        Mockito.when(movieRepository.findAll()).thenReturn(List.of(ENTITY));
        List<MovieDto> expected = List.of(DTO);

        // Mockito.when
        List<MovieDto> actual = underTest.getMovieList();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(movieRepository).findAll();
    }

    @Test
    void testGetMovieByTitleShouldReturnTitleWhenInputMovieTitleIsTitle() {
        // Given
        Mockito.when(movieRepository.findByTitle("Kung-Fu Panda")).thenReturn(ENTITY);
        Optional<MovieDto> expected = Optional.of(DTO);

        // Mockito.when
        Optional<MovieDto> actual = underTest.getMovieByTitle("Kung-Fu Panda");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(movieRepository).findByTitle("Kung-Fu Panda");
    }

    @Test
    void testCreateMovieShouldStoreTheGivenMovieWhenTheInputMovieIsValid() {
        // Given
        Mockito.when(movieRepository.save(ENTITY)).thenReturn(ENTITY);

        // Mockito.when
        underTest.createMovie(DTO);

        // Then
        Mockito.verify(movieRepository).save(ENTITY);
    }

}
