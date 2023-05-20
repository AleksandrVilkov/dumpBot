package com.dumpBot.processor.webAppProcessor;

import com.dumpBot.model.enums.Action;
import com.dumpBot.processor.webAppProcessor.process.RegistrationWebAppProcess;
import com.dumpBot.processor.webAppProcessor.process.SaleWebAppProcess;
import com.dumpBot.processor.webAppProcessor.process.SearchWebAppProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebAppProcessFactory {
    private static WebAppProcess registration;
    private static WebAppProcess search;
    private static WebAppProcess sale;

    @Autowired
    public WebAppProcessFactory(RegistrationWebAppProcess r, SearchWebAppProcess s, SaleWebAppProcess sp) {
        WebAppProcessFactory.registration = r;
        WebAppProcessFactory.search = s;
        WebAppProcessFactory.sale = sp;
    }

    public static WebAppProcess getProcess(Action action) {
        switch (action) {
            case REGISTRATION -> {
                return registration;
            }
            case SEARCH -> {
                return search;
            }
            case SALE -> {
                return sale;
            }

            default -> {
                return null;
            }
        }
    }
}
