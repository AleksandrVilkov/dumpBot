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
        logger.writeInfo("start sale ready process for user " + userId);
        User user = userStorage.getUser(userId);
        if (user == null) {
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
            List<SendMessage> msgs = new ArrayList<>();
            msgs.add(new SendMessage(userId, resources.getMsgs().getPhoto().getNoPhoto()));
            msgs.add(new SendMessage(userId, resources.getMsgs().getPhoto().getInfo()));
            return msgs;
        }

        Car car = carStorage.findCarById(Integer.parseInt(lastCallback.getCarId()));
        logger.writeInfo("find car for user " + user.getLogin());
        City city = cityStorage.getCityById(Integer.parseInt(user.getRegionId()));
        logger.writeInfo("find city for user " + user.getLogin());
        UserAccommodation userAccommodation = ReadyUtils.createUserAccommodation(lastCallback, user, car, city);
        if (accommodationStorage.saveAccommodation(userAccommodation)) {
            updateUser(user);
            List<SendMessage> result = new ArrayList<>(getAccommodationMsgForAdmins());
            result.add(new SendMessage(userId, resources.getMsgs().getSale().getSuccessSendQuery()));
            return result;
        } else {
            return sendError(userId);
        }
    }

    private List<SendMessage> getAccommodationMsgForAdmins() {
        List<SendMessage> result = new ArrayList<>();
        List<User> admins = userStorage.findAdmins();
        for (User u : admins) {
            String id = String.valueOf(u.getLogin());
            result.add(new SendMessage(id, resources.getMsgs().getAdmin().getNewAccommodation()));
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
// Перенести в функционал админа.
//Sale
//Если более 1 фото - нужно отправлять SendMediaGroup, если фото одно - то SendPhoto
//    private List<InputMedia> getMedias(LastCallback lastCallback, User user) {
//        List<InputMedia> inputMedia = new ArrayList<>();
//        boolean isFirst = true;
//        for (String photo : lastCallback.getPhotos()) {
//            InputMediaPhoto inputMediaPhoto = new InputMediaPhoto(photo);
//            if (isFirst) {
//                inputMediaPhoto.setCaption(crateTextForChanel(lastCallback, user));
//                isFirst = false;
//            }
//            inputMedia.add(inputMediaPhoto);
//        }
//        return inputMedia;
//    }
//        if (lastCallback.getPhotos().size() == 1) {
//            SendPhoto sendPhoto = new SendPhoto();
//            sendPhoto.setPhoto(new InputFile(lastCallback.getPhotos().get(0)));
//            sendPhoto.setChatId(config.getValidateData().getChannelID());
//            sendPhoto.setCaption(crateTextForChanel(lastCallback, user));
//            try {
//
//                //TODO сохранять объявление в бд, слать уведомление админам
//                bot.execute(sendPhoto);
//                updateUser(user);
//                return Collections.singletonList(new SendMessage(userId, "Успешно отправлено в канал!"));
//            } catch (TelegramApiException e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            SendMediaGroup sendMediaGroup = new SendMediaGroup();
//            sendMediaGroup.setMedias(getMedias(lastCallback, user));
//            sendMediaGroup.setChatId(config.getValidateData().getChannelID());
//            try {
//
//                //TODO сохранять объявление в бд, слать уведомление админам
//                bot.execute(sendMediaGroup);
//                updateUser(user);
//                return Collections.singletonList(new SendMessage(userId, "Успешно отправлено в канал!"));
//            } catch (TelegramApiException e) {
//                logger.writeStackTrace(e);
//                return sendError(userId);
//            }
//        }



//Search

//        if (lastCallback.getPhotos() == null || lastCallback.getPhotos().size() == 0) {
//            //фото нет
//            return handleCallbackWithoutPhoto(lastCallback, user);
//        } else if (lastCallback.getPhotos().size() == 1) { //есть одно фото
//            return handleCallbackWithOnePhoto(lastCallback, user);
//        } else { //есть много фото
//            return handleCallbackWithMorePhoto(lastCallback, user);
//        }

//    private List<SendMessage> handleCallbackWithoutPhoto(LastCallback lastCallback, User user) {
//        logger.writeInfo("no photo in user callback");
//        SendMessage sendMessage = new SendMessage(String.valueOf(config.getValidateData().getChannelID()),
//                crateTextForChanel(lastCallback, user, false));
//        try {
//            bot.execute(sendMessage);
//            updateUser(user);
//            return Collections.singletonList(new SendMessage((user.getLogin()),
//                    resources.getMsgs().getSearch().getSuccessSendSearchQuery()));
//        } catch (TelegramApiException e) {
//            logger.writeStackTrace(e);
//            return sendError(String.valueOf(user.getId()));
//        }
//    }
//
//    private List<SendMessage> handleCallbackWithOnePhoto(LastCallback lastCallback, User user) {
//        logger.writeInfo("find one photo in callback");
//        SendPhoto sendPhoto = new SendPhoto();
//        sendPhoto.setPhoto(new InputFile(lastCallback.getPhotos().get(0)));
//        sendPhoto.setChatId(config.getValidateData().getChannelID());
//        sendPhoto.setCaption(crateTextForChanel(lastCallback, user, true));
//        try {
//            bot.execute(sendPhoto);
//            updateUser(user);
//            return Collections.singletonList(new SendMessage(user.getLogin(),
//                    resources.getMsgs().getSearch().getSuccessSendSearchQuery()));
//        } catch (TelegramApiException e) {
//            logger.writeStackTrace(e);
//            return sendError(String.valueOf(user.getId()));
//        }
//
//    }
//
//    private List<SendMessage> handleCallbackWithMorePhoto(LastCallback lastCallback, User user) {
//        logger.writeInfo("find more photo in callback");
//        SendMediaGroup sendMediaGroup = new SendMediaGroup();
//        sendMediaGroup.setMedias(getMedias(lastCallback, user));
//        sendMediaGroup.setChatId(config.getValidateData().getChannelID());
//        try {
//            bot.execute(sendMediaGroup);
//            updateUser(user);
//            return Collections.singletonList(new SendMessage(user.getLogin(),
//                    resources.getMsgs().getSearch().getSuccessSendSearchQuery()));
//        } catch (TelegramApiException e) {
//            logger.writeStackTrace(e);
//            return sendError(String.valueOf(user.getId()));
//        }
//    }

//    private List<InputMedia> getMedias(LastCallback lastCallback, User user) {
//        List<InputMedia> inputMedia = new ArrayList<>();
//        boolean isFirst = true;
//        for (String photo : lastCallback.getPhotos()) {
//            InputMediaPhoto inputMediaPhoto = new InputMediaPhoto(photo);
//            if (isFirst) {
//                inputMediaPhoto.setCaption(crateTextForChanel(lastCallback, user, true));
//                isFirst = false;
//            }
//            inputMedia.add(inputMediaPhoto);
//        }
//        return inputMedia;
//    }