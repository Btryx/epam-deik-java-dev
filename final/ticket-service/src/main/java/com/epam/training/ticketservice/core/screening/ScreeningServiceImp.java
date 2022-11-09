package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.model.Movie;
import com.epam.training.ticketservice.core.room.model.Room;
import com.epam.training.ticketservice.core.screening.model.Screening;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Service
public class ScreeningServiceImp implements ScreeningService {

    private List<Screening> screeningList;

    @Override
    public void initScreenings() throws ParseException {

        screeningList = new LinkedList<>(List.of());
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
