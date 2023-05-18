package com.dumpBot.processor.webAppProcessor.process;

import com.dumpBot.loger.ILogger;
import com.dumpBot.model.LastCallback;
import com.dumpBot.model.User;
import com.dumpBot.model.WebAppData;
import com.dumpBot.storage.IUserStorage;
import com.dumpBot.resources.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;


@Component
public class SaleWebAppProcess implements WebAppProcess {
    @Autowired
    IUserStorage userStorage;
    @Autowired
    Resources resources;

    @Autowired
    ILogger logger;

    @Override
    public boolean processData(Update update, WebAppData webAppData) {
        String userId = update.getMessage().getFrom().getId().toString();
        User user = userStorage.getUser(userId);
        logger.writeInfo("user " + userId + " was found from database");
        user.setWaitingMessages(true);
        user.setClientAction(webAppData.getAction());
        LastCallback lastCallback = new LastCallback();
        lastCallback.setDescription(webAppData.getDescription());
        lastCallback.setPrice(webAppData.getPrice());
        lastCallback.setCarId(webAppData.getCarId());
        user.setLastCallback(lastCallback.toString());
        userStorage.saveUser(user);
        logger.writeInfo("user update successfully with data: " + user.toString());
        return true;
    }

    @Override
    public List<SendMessage> prepareAnswer(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        List<SendMessage> result = new ArrayList<>();
        result.add(new SendMessage(userId, resources.getMsgs().getPhoto().getGivePhoto()));
        result.add(new SendMessage(userId, resources.getMsgs().getPhoto().getInfo()));
        return result;
    }
}
