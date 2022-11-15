package com.epam.training.ticketservice.core.room.persistence;


import com.epam.training.ticketservice.core.movie.persistence.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    Room findByName(String name);
}
