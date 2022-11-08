package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.model.Movie;
import com.epam.training.ticketservice.core.room.model.Room;
import com.epam.training.ticketservice.core.screening.model.Screening;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ScreeningServiceImp implements ScreeningService {

    private List<Screening> screeningList;

    @Override
    public void initScreenings() throws ParseException {

        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .parse("2021-03-15 11:00");

        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .parse("2022-03-15 11:00");

        screeningList = new LinkedList<>(List.of(
                Screening.builder()
                        .withMovie("IT")
                        .withRoom("2d")
                        .withTime(date1)
                        .build(),
                Screening.builder()
                        .withMovie("Tangled")
                        .withRoom("3d")
                        .withTime(date2)
                        .build()));
    }

    @Override
    public void createScreening(Screening screening) {
        screeningList.add(screening);
    }

    @Override
    public List<Screening> getScreeningList() {
        return screeningList;
    }

    @Override
    public void deleteScreening(Screening screening) {
        screeningList.remove(screening);
    }

}
