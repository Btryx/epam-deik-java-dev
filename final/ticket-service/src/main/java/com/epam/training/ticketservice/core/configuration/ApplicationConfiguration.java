package com.epam.training.ticketservice.core.configuration;

import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.ScreeningServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfiguration {

    @Bean(initMethod = "initScreenings")
    public ScreeningService screeningService() {
        return new ScreeningServiceImp();
    }

}
