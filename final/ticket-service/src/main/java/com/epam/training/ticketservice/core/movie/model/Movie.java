package com.epam.training.ticketservice.core.movie.model;


import lombok.Value;

@Value
public class Movie {

    String title;
    String genre;
    int length;

    @Override
    public String toString() {
        return   "\n" + title + " ("
                 + genre + " "
                 + length + " minutes)";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        String genre;
        int length;

        public Builder withtitle(String title) {
            this.title = title;
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
            return new Movie(title, genre, length);
        }
    }
}
