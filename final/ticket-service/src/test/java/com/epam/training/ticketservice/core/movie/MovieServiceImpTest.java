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
    void testGetMovieListShouldReturnALLMovies() {
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
    void testGetMovieByTitleShouldReturnTitleWhenInputMovieTitleExists() {
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

    @Test
    void testMovieShouldNotBeCreatedIfTitleAlreadyExists(){

        //Given
        MovieDto newMovie = new MovieDto("Kung-Fu Panda", "Animation", 120);

        //When
        Mockito.when(movieRepository.existsByTitle(newMovie.getTitle())).thenReturn(true);


        //Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> { underTest.createMovie(newMovie);});
    }


    @Test
    public void testCreateMovieShouldThrowNullPointerExceptionWhenMovieIsNull() {
        // Given

        // When
        Assertions.assertThrows(NullPointerException.class,
                () -> underTest.createMovie(null));

        // Then
        Mockito.verifyNoMoreInteractions(movieRepository);
    }


    @Test
    void testMovieShouldNotBeUpdatedIfMovieDoesNotExist(){

        //Given
        MovieDto newMovie = new MovieDto("Kung-Fu Panda", "Animation", 120);

        //When
        Mockito.when(movieRepository.existsByTitle(newMovie.getTitle())).thenReturn(false);


        //Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> { underTest.updateMovie(
                newMovie.getTitle(),
                newMovie.getGenre(),
                newMovie.getLength()
        );});
    }

    @Test
    public void testUpdateMovieShouldModifyTheEntityWhenTheInputIsValid() {
        //Given
        Movie movie = new Movie("Tangled", "animationn", 125);
        Mockito.when(movieRepository
                        .findByTitle("Tangled")).thenReturn(movie);
        Mockito.when(movieRepository
                        .existsByTitle("Tangled")).thenReturn(true);

        MovieDto requiredMovie = new MovieDto.Builder()
                .withTitle("Tangled")
                .withGenre("animation")
                .withLength(400)
                .build();

        Movie expected = new Movie(requiredMovie.getTitle(),
                requiredMovie.getGenre(),
                requiredMovie.getLength());
        //When
        underTest.updateMovie(requiredMovie.getTitle(),
                requiredMovie.getGenre(),
                requiredMovie.getLength());

        //Then
        Assertions.assertEquals(expected, movieRepository
                .findByTitle("Tangled"));
        Mockito.verify(movieRepository)
                .existsByTitle("Tangled");
        Mockito.verify(movieRepository, Mockito.times(2))
                .findByTitle("Tangled");
        Mockito.verify(movieRepository)
                .save(expected);
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testWhenTheInputValidThenDeleteMovie() {

        //Given
        Mockito.when(movieRepository
                        .existsByTitle("Tangled"))
                .thenReturn(true);

        //When
        underTest.deleteMovieByTitle("Tangled");

        //Then
        Mockito.verify(movieRepository)
                .existsByTitle("Tangled");
        Mockito.verify(movieRepository)
                .deleteMovieByTitle("Tangled");

        Mockito.verifyNoMoreInteractions(movieRepository);

    }

    @Test
    public void testWhenMovieDoesNotExistThenDeleteMovieShouldThrowIllegalArgException(){
        //Given
        Mockito.when(movieRepository
                        .existsByTitle("Tangled"))
                .thenReturn(false);

        //Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> { underTest.deleteMovieByTitle("Tangled");});
    }

    @Test
    public void testMovieFindByTitleShouldReturnMovieIfItExists(){
        //Given
        Mockito.when(movieRepository
                            .existsByTitle(ENTITY.getTitle()))
                .thenReturn(true);

        //When
        underTest.findByTitle(ENTITY.getTitle());

        //Then
        Mockito.verify(movieRepository).findByTitle(ENTITY.getTitle());
    }

}
