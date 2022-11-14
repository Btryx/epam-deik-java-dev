package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.text.ParseException;
import java.util.List;

public interface ScreeningService {

    void initScreenings() throws ParseException;

    void createScreening(ScreeningDto screeningDto);

    List<ScreeningDto> getScreeningList();

    void deleteScreening(ScreeningDto screeningDto);

}
