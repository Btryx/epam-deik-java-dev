package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.Value;

import java.text.SimpleDateFormat;
import java.util.Date;

@Value
public class ScreeningDto {

    MovieDto movieDto;
    RoomDto roomDto;
    Date time;

    @Override
    public String toString() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String strDate= formatter.format(time);

        return movieDto + ", screened in room \"" + roomDto.getName() + "\", at " + strDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private MovieDto movieDto;
        private RoomDto roomDto;
        private Date time;

        public Builder withMovie(MovieDto movieDto) {
            this.movieDto = movieDto;
            return this;
        }

        public Builder withRoom(RoomDto roomDto) {
            this.roomDto = roomDto;
            return this;
        }

        public Builder withTime(Date time) {
            this.time = time;
            return this;
        }

        public ScreeningDto build() {
            return new ScreeningDto(movieDto, roomDto, time);
        }
    }
}
