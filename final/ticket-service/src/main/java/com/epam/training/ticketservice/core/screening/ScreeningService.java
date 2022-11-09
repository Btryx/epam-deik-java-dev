package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.model.Screening;

import java.text.ParseException;
import java.util.List;

public interface ScreeningService {

    void initScreenings() throws ParseException;

    void createScreening(Screening screening);

    List<Screening> getScreeningList();

    void deleteScreening(Screening screening);

}
