package com.dumpBot.processor.msgProcessor.process.ready;

import com.dumpBot.bot.Bot;
import com.dumpBot.common.Util;
import com.dumpBot.config.Config;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.Car;
import com.dumpBot.model.City;
import com.dumpBot.model.LastCallback;
import com.dumpBot.model.User;
import com.dumpBot.processor.IUserStorage;
import com.dumpBot.resources.Resources;
import com.dumpBot.service.ICarStorage;
import com.dumpBot.service.ICityStorage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс, отвечающий за обработку команды ready при статусе пользователя SEARCH
 */
@Component
public class SearchReadyProcess implements IReadyProcess {
    @Autowired
    Bot bot;
    @Autowired
    ILogger logger;
    @Autowired
    ICarStorage carStorage;
    @Autowired
    ICityStorage cityStorage;
    @Autowired
    IUserStorage userStorage;
    @Autowired
    Config config;
    @Autowired
    Resources resources;

    @Override
    public List<SendMessage> execute(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start search ready process for user " + userId);
        User user = userStorage.getUser(userId);
        if (user == null) {
            return sendError(userId);
        }

        LastCallback lastCallback;
        try {
            lastCallback = Util.readLastCallback(user.getLastCallback());
            logger.writeInfo("read user callback: " + lastCallback.toString());
        } catch (JsonProcessingException e) {
            logger.writeStackTrace(e);
            return sendError(userId);
        }

        if (lastCallback.getPhotos() == null || lastCallback.getPhotos().size() == 0) {  //фото нет
            return handleCallbackWithoutPhoto(lastCallback, user);
        } else if (lastCallback.getPhotos().size() == 1) { //есть одно фото
            return handleCallbackWithOnePhoto(lastCallback, user);
        } else { //есть много фото
            return handleCallbackWithMorePhoto(lastCallback, user);
        }
    }

    private List<SendMessage> handleCallbackWithoutPhoto(LastCallback lastCallback, User user) {
        logger.writeInfo("no photo in user callback");
        SendMessage sendMessage = new SendMessage(String.valueOf(config.getValidateData().getChannelID()),
                crateTextForChanel(lastCallback, user, false));
        try {
            bot.execute(sendMessage);
            updateUser(user);
            return Collections.singletonList(new SendMessage((user.getLogin()),
                    resources.getMsgs().getSearch().getSuccessSendSearchQuery()));
        } catch (TelegramApiException e) {
            logger.writeStackTrace(e);
            return sendError(String.valueOf(user.getId()));
        }
    }

    private List<SendMessage> handleCallbackWithOnePhoto(LastCallback lastCallback, User user) {
        logger.writeInfo("find one photo in callback");
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(lastCallback.getPhotos().get(0)));
        sendPhoto.setChatId(config.getValidateData().getChannelID());
        sendPhoto.setCaption(crateTextForChanel(lastCallback, user, true));
        try {
            bot.execute(sendPhoto);
            updateUser(user);
            return Collections.singletonList(new SendMessage(user.getLogin(),
                    resources.getMsgs().getSearch().getSuccessSendSearchQuery()));
        } catch (TelegramApiException e) {
            logger.writeStackTrace(e);
            return sendError(String.valueOf(user.getId()));
        }

    }

    private List<SendMessage> handleCallbackWithMorePhoto(LastCallback lastCallback, User user) {
        logger.writeInfo("find more photo in callback");
        SendMediaGroup sendMediaGroup = new SendMediaGroup();
        sendMediaGroup.setMedias(getMedias(lastCallback, user));
        sendMediaGroup.setChatId(config.getValidateData().getChannelID());
        try {
            bot.execute(sendMediaGroup);
            updateUser(user);
            return Collections.singletonList(new SendMessage(user.getLogin(),
                    resources.getMsgs().getSearch().getSuccessSendSearchQuery()));
        } catch (TelegramApiException e) {
            logger.writeStackTrace(e);
            return sendError(String.valueOf(user.getId()));
        }
    }

    private List<InputMedia> getMedias(LastCallback lastCallback, User user) {
        List<InputMedia> inputMedia = new ArrayList<>();
        boolean isFirst = true;
        for (String photo : lastCallback.getPhotos()) {
            InputMediaPhoto inputMediaPhoto = new InputMediaPhoto(photo);
            if (isFirst) {
                inputMediaPhoto.setCaption(crateTextForChanel(lastCallback, user, true));
                isFirst = false;
            }
            inputMedia.add(inputMediaPhoto);
        }
        return inputMedia;
    }

    private List<SendMessage> sendError(String userId) {
        return Collections.singletonList(new SendMessage(userId, resources.getErrors().getCommonError()));
    }

    private void updateUser(User user) {
        user.setWaitingMessages(false);
        user.setLastCallback(null);
        user.setClientAction(null);
        userStorage.saveUser(user);
        logger.writeInfo("callback was clean. Update user successfully");
    }

    private String crateTextForChanel(LastCallback lastCallback, User user, boolean withPhoto) {
        String carId = lastCallback.getCarId();
        Car car = null;
        if (carId != null && !carId.equalsIgnoreCase("")) {
            logger.writeInfo("in callback was found carId: " + carId + ". Going to database...");
            car = carStorage.findCarById(Integer.parseInt(lastCallback.getCarId()));
        }
        City city = cityStorage.getCityById(Integer.parseInt(user.getRegionId()));
        logger.writeInfo("find city " + city.getName() + " for user " + user.getLogin());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Куплю: ").append(lastCallback.getDescription()).append("\n");
        if (car != null) {
            stringBuilder.append("Концерн: ")
                    .append(car.getConcern().getName()).append("\n")
                    .append("Бренд: ")
                    .append(car.getBrand().getName())
                    .append("\n")
                    .append("Модель: ")
                    .append(car.getModel().getName()).append("\n");
        }
        if (lastCallback.getPrice() != null && !lastCallback.getPrice().equals("")) {
            stringBuilder.append("Интересна цена до: ")
                    .append(lastCallback.getPrice()).append("\n");
        }
        stringBuilder.append("Писать: @")
                .append(user.getUserName())
                .append("\n")
                .append("Местонахождение: ")
                .append(city.getName());

        return stringBuilder.toString();
    }
}
