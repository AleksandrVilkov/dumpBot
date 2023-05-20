package com.dumpBot.processor.msgProcessor.process.admin;

import com.dumpBot.processor.msgProcessor.process.admin.handlers.StatisticHandler;
import com.dumpBot.processor.msgProcessor.process.admin.handlers.TextMsgHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class HandlerFactory {
    private static StatisticHandler statisticHandler;

    @Autowired
    public HandlerFactory(StatisticHandler sh) {
        HandlerFactory.statisticHandler = sh;
    }

    public static TextMsgHandler getHandler(Message message) {
        //TODO вынести в констатны все это добро
        switch (message.getText()) {
            case "Статистика" -> {
                return statisticHandler;
            }
            default -> {
                return null;
            }
        }
    }
}
