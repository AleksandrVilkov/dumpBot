package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.bot.Bot;
import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.User;
import com.dumpBot.model.UserSearchRequest;
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
public class ReadyProcess extends BaseProcess implements MsgProcess {
    @Autowired
    private IStorage storage;
    @Autowired
    private ILogger logger;
    @Autowired
    private Bot bot;
    @Autowired
    private ResourcesHelper resourcesHelper;


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
        SendMediaGroup mediaGroup = new SendMediaGroup();
        if (callback != null) {
            String textMsg = createText(callback, user);
            mediaGroup.setChatId(String.valueOf(bot.getConfig().getValidateData().getChannelID()));
            List<InputMedia> media = new ArrayList<>();
        /*
        в поле media должен быть указан массив с фото (видео) и поле caption должно быть только у первого элемента массива.
        Если указать caption для более чем одного элемента, то Телеграм будет отображать их только при нажатии
        на предварительный просмотр фотографии для каждой фотографии отдельно.
        */

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
            saveSearchTerms(user, textMsg, callback.getPhotos());
            resetLastUserAction(user);
            return new SendMessage(userId, resourcesHelper.getResources().getMsgs().getSendingModeration());
        }
        return new SendMessage(userId, resourcesHelper.getResources().getMsgs().getNoSendingModeration());
    }

    private void saveSearchTerms(User user, String textMsg, List<String> photos) {
        UserSearchRequest userSearchRequest = new UserSearchRequest();
        userSearchRequest.setCreatedDate(new Date());
        userSearchRequest.setClientLogin(user.getLogin());
        userSearchRequest.setClientId(user.getId());
        userSearchRequest.setMinPrice(0);
        userSearchRequest.setMaxPrice(0);
        userSearchRequest.setApproved(false);
        userSearchRequest.setRejected(false);
        userSearchRequest.setTopical(true);
        userSearchRequest.setDescription(textMsg);
        userSearchRequest.setPhotos(photos);
        storage.saveSearchTerms(userSearchRequest);
    }

    private String createText(Callback callback, User user) {
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

    private void resetLastUserAction(User user) {
        user.setWaitingMessages(false);
        user.setClientAction(null);
        user.setLastCallback(null);
        storage.saveUser(user);

    }
}
