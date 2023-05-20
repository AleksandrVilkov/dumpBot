package com.dumpBot.processor.callbackProcessor.process;

import com.dumpBot.bot.Bot;
import com.dumpBot.config.Config;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.UserAccommodation;
import com.dumpBot.model.enums.AccommodationType;
import com.dumpBot.processor.callbackProcessor.CallbackProcess;
import com.dumpBot.storage.IAccommodationStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class ApprovedProcess implements CallbackProcess {
    @Autowired
    Bot bot;
    @Autowired
    ILogger logger;

    @Autowired
    Config config;

    @Autowired
    IAccommodationStorage accommodationStorage;

    //TODO подклчюить ресурсы
    @Override
    public List<SendMessage> start(UserAccommodation userAccommodation, Update update) {
        List<SendMessage> result = new ArrayList<>();

        if (!sendToChannel(userAccommodation)) {
            return sendErr(update);
        }

        List<SendMessage> adminMsgs;
        try {
            adminMsgs = createMsgToAdmin(update, userAccommodation);
        } catch (Exception e) {
            logger.writeStackTrace(e);
            return sendErr(update);
        }

        List<SendMessage> userMsgs = createMsgToUser(userAccommodation);
        updateUserAccommodation(userAccommodation);
        result.addAll(adminMsgs);
        result.addAll(userMsgs);
        return result;
    }

    private List<SendMessage> createMsgToAdmin(Update update, UserAccommodation userAccommodation) throws Exception {
        int msgId = update.getCallbackQuery().getMessage().getMessageId();
        String fromId = String.valueOf(update.getCallbackQuery().getFrom().getId());
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(fromId);
        editMessageText.setText("UPD: Запрос " + userAccommodation.getId() + " от пользователя "
                + userAccommodation.getClientLogin() + " успешно отправлен в канал. Пользователю отправлено соответсвующее сообщение");
        editMessageText.setMessageId(msgId);
        bot.execute(editMessageText);
        return new ArrayList<>();
    }

    private List<SendMessage> sendErr(Update update) {
        return Collections.singletonList(new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()), "Произошла ошибка"));
    }

    private List<SendMessage> createMsgToUser(UserAccommodation userAccommodation) {
        String text1 = "Твой запрос " + userAccommodation.getId() + " был успешно проверен!";
        String text2 = "Он размещен в канале, и доступен к просмотру. Найти его можешь в 'ссылка на канал'";
        SendMessage f = new SendMessage(userAccommodation.getClientLogin(), text1);
        SendMessage s = new SendMessage(userAccommodation.getClientLogin(), text2);
        List<SendMessage> result = new ArrayList<>();
        result.add(f);
        result.add(s);
        return result;
    }

    private boolean sendToChannel(UserAccommodation userAccommodation) {
        if (userAccommodation.getType().equals(AccommodationType.SALE)) {
            try {
                createAndSendSaleToChannel(userAccommodation);
            } catch (TelegramApiException e) {
                logger.writeStackTrace(e);

            }
        } else if (userAccommodation.getType().equals(AccommodationType.SEARCH)) {
            try {
                createAndSendSearchToChannel(userAccommodation);
            } catch (Exception e) {
                logger.writeStackTrace(e);
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private void createAndSendSaleToChannel(UserAccommodation userAccommodation) throws TelegramApiException {
        if (userAccommodation.getPhotos().size() == 1) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto(new InputFile(userAccommodation.getPhotos().get(0)));
            sendPhoto.setChatId(config.getValidateData().getChannelID());
            sendPhoto.setCaption(userAccommodation.getDescription());
            bot.execute(sendPhoto);
            logger.writeInfo("sale send to channel " + config.getValidateData().getChannelID() + " with 1 photo successfully");
        } else {
            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setMedias(getMedias(userAccommodation));
            sendMediaGroup.setChatId(config.getValidateData().getChannelID());
            bot.execute(sendMediaGroup);
            logger.writeInfo("sale send to channel " + config.getValidateData().getChannelID() + " with many photo successfully");
        }
    }

    private void createAndSendSearchToChannel(UserAccommodation userAccommodation) throws Exception {
        if (userAccommodation.getPhotos() == null || userAccommodation.getPhotos().size() == 0) {
            logger.writeInfo("no photo in user callback");
            SendMessage sendMessage = new SendMessage(String.valueOf(config.getValidateData().getChannelID()), userAccommodation.getDescription());
            bot.execute(sendMessage);
            logger.writeInfo("search send to channel " + config.getValidateData().getChannelID() + " without photo successfully");
            return;
        }
        if (userAccommodation.getPhotos().size() == 1) {
            logger.writeInfo("find one photo in callback");
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto(new InputFile(userAccommodation.getPhotos().get(0)));
            sendPhoto.setChatId(config.getValidateData().getChannelID());
            sendPhoto.setCaption(userAccommodation.getDescription());
            bot.execute(sendPhoto);
            logger.writeInfo("search send to channel " + config.getValidateData().getChannelID() + " with 1 photo successfully");
        } else {
            logger.writeInfo("find more photo in callback");
            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setMedias(getMedias(userAccommodation));
            sendMediaGroup.setChatId(config.getValidateData().getChannelID());
            bot.execute(sendMediaGroup);
            logger.writeInfo("search send to channel " + config.getValidateData().getChannelID() + " with many photo successfully");
        }
    }

    private void updateUserAccommodation(UserAccommodation userAccommodation) {
        userAccommodation.setApproved(true);
        userAccommodation.setTopical(false);
        userAccommodation.setCreatedDate(new Date());
        accommodationStorage.saveAccommodation(userAccommodation);
        logger.writeInfo("userAccommodation " + userAccommodation.getId() + " was updated");
    }

    private List<InputMedia> getMedias(UserAccommodation userAccommodation) {
        List<InputMedia> inputMedia = new ArrayList<>();
        boolean isFirst = true;
        for (String photo : userAccommodation.getPhotos()) {
            InputMediaPhoto inputMediaPhoto = new InputMediaPhoto(photo);
            if (isFirst) {
                inputMediaPhoto.setCaption(userAccommodation.getDescription());
                isFirst = false;
            }
            inputMedia.add(inputMediaPhoto);
        }
        return inputMedia;
    }
}


