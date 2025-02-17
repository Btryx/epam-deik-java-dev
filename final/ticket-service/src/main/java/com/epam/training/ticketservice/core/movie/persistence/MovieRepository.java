package com.epam.training.ticketservice.core.movie.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> {

    Movie findByTitle(String title);

    Boolean existsByTitle(String title);

    void deleteMovieByTitle(String title);
}
