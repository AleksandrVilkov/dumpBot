package com.dumpBot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
public class WebApp {
    @Value("${webApp.url}")
    private String url;
    @Value("${webApp.pathSale}")
    private String pathSale;
    @Value("${webApp.pathRegistration}")
    private String pathRegistration;
    @Value("${webApp.pathSearch}")
    private String pathSearch;
}
