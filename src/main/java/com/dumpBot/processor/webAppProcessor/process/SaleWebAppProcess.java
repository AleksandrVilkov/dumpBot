package com.dumpBot.processor.webAppProcessor.process;

import com.dumpBot.loger.ILogger;
import com.dumpBot.model.User;
import com.dumpBot.model.WebAppData;
import com.dumpBot.processor.IUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


@Component
public class SaleWebAppProcess implements WebAppProcess {
    @Autowired
    IUserStorage storage;

    @Autowired
    ILogger logger;

    @Override
    public boolean processData(Update update, WebAppData webAppData) {
        String userId = update.getMessage().getFrom().getId().toString();
        User user = storage.getUser(userId);
        logger.writeInfo("user " + userId + " was found from database");
        user.setWaitingMessages(true);
        user.setClientAction(webAppData.getAction());
        user.setLastCallback(webAppData.toString());
        storage.saveUser(user);
        logger.writeInfo("user update successfully with data: " + user.toString());
        return true;
    }

    @Override
    public List<SendMessage> prepareAnswer(Update update) {
        return null;
    }
}
