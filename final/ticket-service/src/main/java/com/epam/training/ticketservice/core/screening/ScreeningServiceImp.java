package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

@Service
public class ScreeningServiceImp implements ScreeningService {

    private List<ScreeningDto> screeningDtoList;

    @Override
    public void initScreenings() throws ParseException {

        screeningDtoList = new LinkedList<>(List.of());
    }

    @Override
    public void createScreening(ScreeningDto screeningDto) {
        screeningDtoList.add(screeningDto);
    }

    @Override
    public List<ScreeningDto> getScreeningList() {
        return screeningDtoList;
    }

    @Override
    public void deleteScreening(ScreeningDto screeningDto) {
        screeningDtoList.remove(screeningDto);
    }

}
