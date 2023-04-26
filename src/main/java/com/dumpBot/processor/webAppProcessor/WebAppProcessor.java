package com.dumpBot.processor.webAppProcessor;

import com.dumpBot.bot.IWebAppProcessor;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.User;
import com.dumpBot.model.WebAppData;
import com.dumpBot.processor.BaseProcess;
import com.dumpBot.processor.IStorage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class WebAppProcessor extends BaseProcess implements IWebAppProcessor {

    @Autowired
    ILogger logger;
    @Autowired
    IStorage storage;

    @Override
    public SendMessage startWebAppProcessor(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        String data = update.getMessage().getWebAppData().getData();
        WebAppData wp;
        try {
            wp = readWebApp(data);
        } catch (Exception e) {
            logger.writeStackTrace(e);
            //TODO Вернуть ошибку
            return null;
        }
        User user = storage.getUser(userId);
        user.setClientAction(wp.getAction());
        user.setLastCallback(data);
        storage.saveUser(user);

        return createResponse(userId, wp);
    }


    private SendMessage createResponse(String userId, WebAppData webAppData) {
        return null;
    }

    private String createText() {
        return "";
    }

    private static WebAppData readWebApp(String webAppStr) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(webAppStr, WebAppData.class);
    }
}

