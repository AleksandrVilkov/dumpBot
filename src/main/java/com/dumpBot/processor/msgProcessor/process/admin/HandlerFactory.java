package com.dumpBot.processor.msgProcessor.process.admin;

import com.dumpBot.processor.msgProcessor.process.admin.handlers.NewAccommodationHandler;
import com.dumpBot.processor.msgProcessor.process.admin.handlers.StatisticHandler;
import com.dumpBot.processor.msgProcessor.process.admin.handlers.TextMsgHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class HandlerFactory {
    private static TextMsgHandler statisticHandler;
    private static TextMsgHandler newAccommodationHandler;

    @Autowired
    public HandlerFactory(StatisticHandler sh, NewAccommodationHandler newAccommodationHandler) {
        HandlerFactory.statisticHandler = sh;
        HandlerFactory.newAccommodationHandler = newAccommodationHandler;
    }

    public static TextMsgHandler getHandler(Message message) {
        //TODO вынести в констатны все это добро
        switch (message.getText()) {
            case "Статистика" -> {
                return statisticHandler;
            }
            case "Новые запросы" -> {
                return newAccommodationHandler;
            }
            default -> {
                return null;
            }
        }
    }
}
