package com.dumpBot.processor.webAppProcessor.process;

import com.dumpBot.model.enums.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebAppProcessFactory {
    private static WebAppProcess registration;
    private static WebAppProcess search;

    @Autowired
    public WebAppProcessFactory(RegistrationWebAppProcess r, SearchWebAppProcess s) {
        WebAppProcessFactory.registration = r;
        WebAppProcessFactory.search = s;
    }

    public static WebAppProcess getProcess(Action action) {
        switch (action) {
            case REGISTRATION -> {
                return registration;
            }
            case SEARCH -> {
                return search;
            }

            default -> {
                return null;
            }
        }
    }
}
