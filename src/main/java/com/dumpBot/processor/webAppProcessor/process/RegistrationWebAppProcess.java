package com.dumpBot.processor.webAppProcessor.process;

import com.dumpBot.loger.ILogger;
import com.dumpBot.model.*;
import com.dumpBot.model.enums.Role;
import com.dumpBot.processor.webAppProcessor.WebAppProcess;
import com.dumpBot.storage.IUserStorage;
import com.dumpBot.resources.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class RegistrationWebAppProcess implements WebAppProcess {

    @Autowired
    IUserStorage userStorage;
    @Autowired
    Resources resources;
    @Autowired
    ILogger logger;

    @Override
    public boolean processData(Update update, WebAppData webAppData) {
        User user = new User(new Date(),
                Role.USER_ROLE,
                String.valueOf(update.getMessage().getFrom().getId()),
                Integer.parseInt(webAppData.getCityId()),
                Integer.parseInt(webAppData.getCarId()));
        user.setWaitingMessages(false);
        logger.writeInfo("New user defined: " + user.toString(), this.getClass());
        user.setUserName(update.getMessage().getFrom().getUserName());
        return userStorage.saveUser(user);
    }


    @Override
    public List<SendMessage> prepareAnswer(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        List<SendMessage> result = new ArrayList<>();
        result.add(new SendMessage(userId, resources.getSuccess().getWonderful()));
        result.add(new SendMessage(userId, resources.getSuccess().getSuccessReservation()));
        result.add(new SendMessage(userId, resources.getSuccess().getPossibilities()));
        result.add(new SendMessage(userId, resources.getSuccess().getMenuAccess()));
        return result;
    }

}
