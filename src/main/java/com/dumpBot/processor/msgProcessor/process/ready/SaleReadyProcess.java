package com.dumpBot.processor.msgProcessor.process.ready;

import com.dumpBot.bot.Bot;
import com.dumpBot.common.Util;
import com.dumpBot.config.Config;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.*;
import com.dumpBot.storage.IAccommodationStorage;
import com.dumpBot.storage.IUserStorage;
import com.dumpBot.resources.Resources;
import com.dumpBot.storage.ICarStorage;
import com.dumpBot.storage.ICityStorage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс, отвечающий за обработку команды ready при статусе пользователя SALE
 */

@Component
public class SaleReadyProcess implements IReadyProcess {

    @Autowired
    Bot bot;
    @Autowired
    ILogger logger;
    @Autowired
    IUserStorage userStorage;
    @Autowired
    ICarStorage carStorage;
    @Autowired
    ICityStorage cityStorage;
    @Autowired
    IAccommodationStorage accommodationStorage;
    @Autowired
    Resources resources;
    @Autowired
    Config config;

    @Override
    public List<SendMessage> execute(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start sale ready process for user " + userId, this.getClass());
        User user = userStorage.getUser(userId);
        if (user == null) {
            logger.writeWarning("user is null", this.getClass());
            return sendError(userId);
        }
        LastCallback lastCallback;
        try {
            lastCallback = Util.readLastCallback(user.getLastCallback());
        } catch (JsonProcessingException e) {
            logger.writeStackTrace(e);
            return sendError(userId);
        }
        //фото обязательно должно быть. Ну а как иначе?
        if (lastCallback.getPhotos() == null || lastCallback.getPhotos().size() == 0) {
            logger.writeWarning("photos is null or size = 0. user: " + userId, this.getClass());
            List<SendMessage> msgs = new ArrayList<>();
            msgs.add(new SendMessage(userId, resources.getMsgs().getPhoto().getNoPhoto()));
            msgs.add(new SendMessage(userId, resources.getMsgs().getPhoto().getInfo()));
            return msgs;
        }

        List<Car> cars = new ArrayList<>();
        for (String id: lastCallback.getCarIds()) {
            Car car = carStorage.findCarById(Integer.parseInt(id));
            cars.add(car);
        }
        logger.writeInfo("find car for user " + user.getLogin(), this.getClass());
        City city = cityStorage.getCityById(user.getRegionId());
        logger.writeInfo("find city for user " + user.getLogin(), this.getClass());
        UserAccommodation userAccommodation = ReadyUtils.createUserAccommodation(lastCallback, user, cars, city);
        //TODO сохранять все машины к объявлению
        if (accommodationStorage.saveAccommodation(userAccommodation)) {
            updateUser(user);
            logger.writeInfo("User was updated. New user data: \n" + user.toString(), this.getClass());
            List<SendMessage> result = new ArrayList<>(getAccommodationMsgForAdmins());
            result.add(new SendMessage(userId, resources.getMsgs().getSale().getSuccessSendQuery()));
            return result;
        } else {
            logger.writeWarning("accommodation was not save!", this.getClass());
            return sendError(userId);
        }
    }

    private List<SendMessage> getAccommodationMsgForAdmins() {
        List<SendMessage> result = new ArrayList<>();
        List<User> admins = userStorage.findAdmins();
        if (admins != null) {
            for (User u : admins) {
                String id = String.valueOf(u.getLogin());
                result.add(new SendMessage(id, resources.getMsgs().getAdmin().getNewAccommodation()));
            }
        }
        return result;
    }

    private void updateUser(User user) {
        user.setWaitingMessages(false);
        user.setLastCallback(null);
        user.setClientAction(null);
        userStorage.saveUser(user);
    }


    private List<SendMessage> sendError(String userId) {
        return Collections.singletonList(new SendMessage(userId, resources.getErrors().getCommonError()));
    }
}