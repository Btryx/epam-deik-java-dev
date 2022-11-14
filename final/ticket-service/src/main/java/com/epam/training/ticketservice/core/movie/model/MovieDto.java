package com.epam.training.ticketservice.core.movie.model;


import lombok.Value;

@Value
public class MovieDto {

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
        private String genre;
        private int length;

        public Builder withTitle(String title) {
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

        public MovieDto build() {
            return new MovieDto(title, genre, length);
        }
    }
}
