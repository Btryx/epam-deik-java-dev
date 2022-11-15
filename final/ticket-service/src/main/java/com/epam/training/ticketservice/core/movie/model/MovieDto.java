package com.epam.training.ticketservice.core.movie.model;


import lombok.Value;

import java.util.Objects;

@Value
public class MovieDto {

    String title;
    String genre;
    int length;

    @Override
    public String toString() {
        return title + " ("
                + genre + ", "
                + length + " minutes)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieDto movieDto = (MovieDto) o;

        if (length != movieDto.length) return false;
        if (!Objects.equals(title, movieDto.title)) return false;
        return Objects.equals(genre, movieDto.genre);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + length;
        return result;
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
