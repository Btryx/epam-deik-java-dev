package com.epam.training.ticketservice.core.room.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
public class Room {

    @Id
    String name;
    int rows;
    int cols;

    public Room(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public String toString() {
        return "Room "
                + name
                + " with " + rows * cols + " seats, "
                + rows + " rows"
                + " and " + cols + " columns";
    }
}
