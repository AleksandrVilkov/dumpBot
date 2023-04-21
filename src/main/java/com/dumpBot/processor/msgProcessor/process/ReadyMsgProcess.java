package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.bot.Bot;
import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.AccommodationType;
import com.dumpBot.model.Action;
import com.dumpBot.model.User;
import com.dumpBot.model.UserAccommodation;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.model.callback.CarData;
import com.dumpBot.processor.IStorage;
import com.dumpBot.processor.ResourcesHelper;
import lombok.Getter;
import lombok.Setter;
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
import java.util.Date;
import java.util.List;

@Component
@Getter
@Setter
public class ReadyMsgProcess extends BaseMsgProcess implements MsgProcess {
    @Autowired
    private IStorage storage;
    @Autowired
    private ILogger logger;
    @Autowired
    private Bot bot;
    @Autowired
    private ResourcesHelper resourcesHelper;


    @Override
    public void processResultPreviousStep() {

    }

    @Override
    public void preparationCurrentProcess() {

    }

    /*
    в поле media должен быть указан массив с фото (видео) и поле caption должно быть только у первого элемента массива.
    Если указать caption для более чем одного элемента, то Телеграм будет отображать их только при нажатии
    на предварительный просмотр фотографии для каждой фотографии отдельно.
    */
    @Override
    public SendMessage execute(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        User user = storage.getUser(userId);
        String stringCallBack = user.getLastCallback();
        Callback callback = null;
        try {
            callback = Util.readCallBack(stringCallBack);
            logger.writeInfo("callback " + callback.toString() + " was found by user " + userId);
        } catch (Exception e) {
            logger.writeStackTrace(e);
        }

        if (callback != null) {
            Action action = callback.getAction();
            if (action.equals(Action.SALE_ACTION)) {
                return readySale(callback, user);
            }
            if (action.equals(Action.SEARCH_REQUEST_ACTION)) {
                return readySearch(callback, user);
            }
            return new SendMessage(callback.getUserId(), resourcesHelper.getResources().getErrors().getCommonError());
        }
        return new SendMessage(callback.getUserId(), resourcesHelper.getResources().getErrors().getCommonError());
    }

    private SendMessage readySearch(Callback callback, User user) {
        String textMsg = createSearchText(callback, user);
        sendToChannel(callback, textMsg);
        saveUserAccommodation(user, textMsg, callback.getPhotos());
        resetLastUserAction(user);
        return new SendMessage(user.getLogin(), resourcesHelper.getResources().getMsgs().getSendingModeration());
    }

    private SendMessage readySale(Callback callback, User user) {
        String textMsg = createSaleText(callback, user);
        sendToChannel(callback, textMsg);
        saveUserAccommodation(user, textMsg, callback.getPhotos());
        resetLastUserAction(user);
        return new SendMessage(user.getLogin(), resourcesHelper.getResources().getMsgs().getSendingModeration());
    }

    private void sendToChannel(Callback callback, String textMsg) {
        if (callback.getPhotos() == null || callback.getPhotos().size() == 0) {
            sendToChannelWithoutPhoto(textMsg);
            return;
        }
        if (callback.getPhotos().size() == 1) {
            sendToChannelOnePhoto(callback, textMsg);
        } else {
            sendToChannelManyPhoto(callback, textMsg);
        }
    }

    private void sendToChannelWithoutPhoto(String textMsg) {
        try {
            bot.execute(new SendMessage(String.valueOf(bot.getConfig().getValidateData().getChannelID()), textMsg));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendToChannelOnePhoto(Callback callback, String textMsg) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(bot.getConfig().getValidateData().getChannelID()));
        sendPhoto.setCaption(textMsg);
        InputFile inputFile = new InputFile(callback.getPhotos().get(0));
        sendPhoto.setPhoto(inputFile);
        try {
            bot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendToChannelManyPhoto(Callback callback, String textMsg) {
        SendMediaGroup mediaGroup = new SendMediaGroup();
        mediaGroup.setChatId(String.valueOf(bot.getConfig().getValidateData().getChannelID()));
        List<InputMedia> media = new ArrayList<>();
        int i = 0;
        for (String photo : callback.getPhotos()) {
            InputMedia inputMedia = new InputMediaPhoto(photo);
            if (i == 0) {
                inputMedia.setCaption(textMsg);
                i++;
            }
            media.add(inputMedia);
        }
        mediaGroup.setMedias(media);
        try {
            bot.execute(mediaGroup);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveUserAccommodation(User user, String textMsg, List<String> photos) {
        UserAccommodation userAccommodation = new UserAccommodation();
        userAccommodation.setCreatedDate(new Date());
        if (user.getClientAction().equalsIgnoreCase(Action.SEARCH_REQUEST_ACTION.name())) {
            userAccommodation.setType(AccommodationType.SEARCH);
        }
        if (user.getClientAction().equalsIgnoreCase(Action.SALE_ACTION.name())) {
            userAccommodation.setType(AccommodationType.SALE);
        }
        userAccommodation.setClientLogin(user.getLogin());
        userAccommodation.setClientId(user.getId());
        userAccommodation.setMinPrice(0);
        userAccommodation.setMaxPrice(0);
        userAccommodation.setApproved(false);
        userAccommodation.setRejected(false);
        userAccommodation.setTopical(true);
        userAccommodation.setDescription(textMsg);
        userAccommodation.setPhotos(photos);
        storage.saveUserAccommodation(userAccommodation);
    }

    private String createSearchText(Callback callback, User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Куплю: ");
        stringBuilder.append(callback.getDescription()).append("\n");

        CarData carData = callback.getCarData();

        if (carData != null && carData.getConcern() != null) {
            stringBuilder.append("Концерн: ").append(carData.getConcern()).append("\n");
        }
        if (carData != null && carData.getBrand() != null) {
            stringBuilder.append("Бренд: ").append(carData.getBrand()).append("\n");
        }
        if (carData != null && carData.getModel() != null) {
            stringBuilder.append("Модель: ").append(carData.getModel()).append("\n");
        }
        if (carData != null && carData.getEngineName() != null) {
            stringBuilder.append("Двигатель: ").append(carData.getEngineName()).append("\n");
        }
        if (carData != null && carData.getBoltPatternSize() != null) {
            stringBuilder.append("Разболтовка колес: ").append(carData.getBoltPatternSize()).append("\n");
        }

        stringBuilder.append("Писать: @").append(user.getUserName()).append("\n");
        stringBuilder.append("Нахожусь в городе ").append(user.getRegion().getName());
        return stringBuilder.toString();
    }

    private String createSaleText(Callback callback, User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Продам: ");
        stringBuilder.append(callback.getDescription()).append("\n");

        CarData carData = callback.getCarData();

        if (carData != null && carData.getConcern() != null) {
            stringBuilder.append("Концерн: ").append(carData.getConcern()).append("\n");
        }
        if (carData != null && carData.getBrand() != null) {
            stringBuilder.append("Бренд: ").append(carData.getBrand()).append("\n");
        }
        if (carData != null && carData.getModel() != null) {
            stringBuilder.append("Модель: ").append(carData.getModel()).append("\n");
        }
        if (carData != null && carData.getEngineName() != null) {
            stringBuilder.append("Двигатель: ").append(carData.getEngineName()).append("\n");
        }
        if (carData != null && carData.getBoltPatternSize() != null) {
            stringBuilder.append("Разболтовка колес: ").append(carData.getBoltPatternSize()).append("\n");
        }
        if (callback.getPrice() != null && !callback.getPrice().equalsIgnoreCase("")) {
            stringBuilder.append("Цена: ").append(callback.getPrice()).append("\n");
        }

        stringBuilder.append("Писать: @").append(user.getUserName()).append("\n");
        stringBuilder.append("Нахожусь в городе ").append(user.getRegion().getName());
        return stringBuilder.toString();
    }

    private void resetLastUserAction(User user) {
        user.setWaitingMessages(false);
        user.setClientAction(null);
        user.setLastCallback(null);
        storage.saveUser(user);

    }
}
