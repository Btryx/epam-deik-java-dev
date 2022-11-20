package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import lombok.Value;

import java.text.SimpleDateFormat;
import java.util.Date;

@Value
public class ScreeningDto {

    Movie movie;
    Room room;
    Date time;

    @Override
    public String toString() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = formatter.format(time);

        return movie + ", screened in room " + room.getName() + ", at " + strDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Movie movie;
        private Room room;
        private Date time;

        public Builder withMovie(Movie movie) {
            this.movie = movie;
            return this;
        }

        public Builder withRoom(Room room) {
            this.room = room;
            return this;
        }

        public Builder withTime(Date time) {
            this.time = time;
            return this;
        }

        public ScreeningDto build() {
            return new ScreeningDto(movie, room, time);
        }
    }
}
