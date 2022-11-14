package com.epam.training.ticketservice.core.screening.persistence;


import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.persistence.Room;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "movie_title")
    Movie movie;
    @ManyToOne
    @JoinColumn(name = "room_name")
    Room room;
    Date time;

    public Screening(Movie movie, Room room, Date time) {
        this.movie = movie;
        this.room = room;
        this.time = time;
    }
}
