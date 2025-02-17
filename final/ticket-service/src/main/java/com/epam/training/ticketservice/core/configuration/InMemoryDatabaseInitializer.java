package com.epam.training.ticketservice.core.configuration;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.user.persistence.User;
import com.epam.training.ticketservice.core.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile("!prod")
@RequiredArgsConstructor
public class InMemoryDatabaseInitializer {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    @PostConstruct
    public void init() {
        User admin = new User("admin", "admin", User.Role.ADMIN);
        userRepository.save(admin);

    }
}
