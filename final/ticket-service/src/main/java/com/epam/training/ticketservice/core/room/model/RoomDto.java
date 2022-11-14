package com.epam.training.ticketservice.core.room.model;


import lombok.Value;

@Value
public class RoomDto {

    String name;
    int rows;
    int cols;

    @Override
    public String toString() {
        return "\nRoom '"
                 + name + "'" + " with "
                 + rows * cols + " seats, "
                 + rows + " rows and "
                 + cols + " columns";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private int rows;
        private int cols;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withRows(int rows) {
            this.rows = rows;
            return this;
        }

        public Builder withCols(int cols) {
            this.cols = cols;
            return this;
        }

        public RoomDto build() {
            return new RoomDto(name, rows, cols);
        }
    }
}
