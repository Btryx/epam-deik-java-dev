package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.Movie;
import com.epam.training.ticketservice.core.room.model.Room;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Value
public class Screening {

    //TODO these should be type movie and room
    String movie;
    String room;
    Date time;

    @Override
    public String toString() {

        return   "\n" + movie + ", screened in room "
                + room + ", at "
                + time;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String movie;
        private String room;
        private Date time;

        public Builder withMovie(String movie) {
            this.movie = movie;
            return this;
        }

        public Builder withRoom(String room) {
            this.room = room;
            return this;
        }

        public Builder withTime(Date time) {
            this.time = time;
            return this;
        }

        public Screening build() {
            return new Screening(movie, room, time);
        }
    }
}
