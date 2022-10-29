package com.epam.training.ticketservice.core.movie.model;


import lombok.Value;

@Value
public class Movie {

    String name;
    String genre;
    int length;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        String genre;
        int length;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder withLength(int length) {
            this.length = length;
            return this;
        }

        public Movie build() {
            return new Movie(name, genre, length);
        }
    }
}
