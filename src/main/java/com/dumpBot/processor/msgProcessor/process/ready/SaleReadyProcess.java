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

      //Если более 1 фото - нужно отправлять SendMediaGroup, если фото одно - то SendPhoto
        /**
         * В дальнейшем этот блок убрать в админку, заменить на сохранение заявки от пользователя в БД
         * И отправку уведомления админам канала
        */
        if (lastCallback.getPhotos().size() == 1) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto(new InputFile(lastCallback.getPhotos().get(0)));
            sendPhoto.setChatId(config.getValidateData().getChannelID());
            sendPhoto.setCaption(crateTextForChanel(lastCallback, user));
            try {
                bot.execute(sendPhoto);
                return Collections.singletonList(new SendMessage(userId, "Успешно отправлено в канал!"));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setMedias(getMedias(lastCallback, user));
            sendMediaGroup.setChatId(config.getValidateData().getChannelID());
            try {
                bot.execute(sendMediaGroup);
                updateUser(user);
                return Collections.singletonList(new SendMessage(userId, "Успешно отправлено в канал!"));
            } catch (TelegramApiException e) {
                logger.writeStackTrace(e);
                return sendError(userId);
            }
        }
    }

    private void updateUser(User user) {
        user.setWaitingMessages(false);
        user.setLastCallback(null);
        user.setClientAction(null);
        userStorage.saveUser(user);
    }

    private List<InputMedia> getMedias(LastCallback lastCallback, User user) {
        List<InputMedia> inputMedia = new ArrayList<>();
        boolean isFirst = true;
        for (String photo : lastCallback.getPhotos()) {
            InputMediaPhoto inputMediaPhoto = new InputMediaPhoto(photo);
            if (isFirst) {
                inputMediaPhoto.setCaption(crateTextForChanel(lastCallback, user));
                isFirst = false;
            }
            inputMedia.add(inputMediaPhoto);
        }
        return inputMedia;
    }

    private String crateTextForChanel(LastCallback lastCallback, User user) {
        Car car = carStorage.findCarById(Integer.parseInt(lastCallback.getCarId()));
        logger.writeInfo("find car for user " + user.getLogin());
        City city = cityStorage.getCityById(Integer.parseInt(user.getRegionId()));
        logger.writeInfo("find city for user " + user.getLogin());

        return "Продам: " + lastCallback.getDescription() + "\n" +
                "Концерн: " + car.getConcern().getName() + "\n" +
                "Бренд: " + car.getBrand().getName() + "\n" +
                "Модель: " + car.getModel().getName() + "\n" +
                "Цена: " + lastCallback.getPrice() + "\n" +
                "Писать: @" + user.getUserName() + "\n" +
                "Местонахождение: " + city.getName();
    }

    private List<SendMessage> sendError(String userId) {
        return Collections.singletonList(new SendMessage(userId, resources.getErrors().getCommonError()));
    }
}
