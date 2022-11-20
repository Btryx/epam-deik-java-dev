package com.epam.training.ticketservice.core.screening.persistence;


import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.persistence.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    Movie movie;
    @ManyToOne
    Room room;
    Date time;

    public Screening(Movie movie, Room room, Date time) {
        this.movie = movie;
        this.room = room;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Screening screening = (Screening) o;
        return id != null && Objects.equals(id, screening.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
