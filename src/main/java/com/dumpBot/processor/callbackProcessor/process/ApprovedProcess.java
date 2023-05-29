package com.dumpBot.processor.callbackProcessor.process;

import com.dumpBot.bot.Bot;
import com.dumpBot.config.Config;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.User;
import com.dumpBot.model.UserAccommodation;
import com.dumpBot.model.enums.AccommodationType;
import com.dumpBot.processor.callbackProcessor.CallbackProcess;
import com.dumpBot.resources.Resources;
import com.dumpBot.storage.IAccommodationStorage;
import com.dumpBot.storage.IUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
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
    IUserStorage userStorage;
    @Autowired
    IAccommodationStorage accommodationStorage;
    @Autowired
    Resources resources;

    @Override
    public List<SendMessage> start(UserAccommodation userAccommodation, Update update) {
        List<SendMessage> result = new ArrayList<>();
        logger.writeInfo("start approved process for accommodation " + userAccommodation.getId(), this.getClass());
        if (!sendToChannel(userAccommodation)) {
            logger.writeError("send accommodation to channel error", this.getClass());
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
        result.addAll(createNotifyCarOwners(userAccommodation));
        return result;
    }

    private List<SendMessage> createNotifyCarOwners(UserAccommodation userAccommodation) {
        if (userAccommodation.getCar() == null) {
            return Collections.emptyList();
        }
        List<User> users = userStorage.getAllUsersByCarId(userAccommodation.getCar().getId());
        List<SendMessage> result = new ArrayList<>();
        for (User user : users) {
            //TODO убрать в ресурсы
            SendMessage sendMessage = new SendMessage(user.getLogin(), "Привет, в канале появилась запись, которая возможно подойдет к твоему автомобилю. Зайди, посмотри");
            result.add(sendMessage);
        }

        return result;
    }

    private List<SendMessage> createMsgToAdmin(Update update, UserAccommodation userAccommodation) throws Exception {
        int msgId = update.getCallbackQuery().getMessage().getMessageId();
        String fromId = String.valueOf(update.getCallbackQuery().getFrom().getId());
        DeleteMessage deleteMessage = new DeleteMessage(fromId, msgId);
        bot.execute(deleteMessage);
        logger.writeInfo("message " + msgId + "was deleted in chat", this.getClass());
        deleteMsgWithPhotos(msgId, fromId, userAccommodation);
        SendMessage sendMessage = new SendMessage(fromId, resources.getMsgs().getAdmin().getUpd() + " "
                + userAccommodation.getId()
                + " " + resources.getMsgs().getAdmin().getFromUser() + " " + userAccommodation.getClientLogin()
                + " " + resources.getMsgs().getAdmin().getSuccessApproved());
        return Collections.singletonList(sendMessage);
    }

    private void deleteMsgWithPhotos(int msgId, String fromId, UserAccommodation userAccommodation) throws Exception {
        /*Нужно удалить фото. телеграм апи не дает возможности прекрепить к sendMediaGroup клавиатуру, по этому
        подобные сообщения отправляются как группа фото, и за ней тествовое сообщение с кнопками.
        С колбеком приходит id сообщения с текстом. айди сообщений с фото неизестны,
        но мы знаем что они идут строго до сообщения,
        с которым пришел колбек и можем их расчитать.*/

        for (int i = 0; i < userAccommodation.getPhotos().size(); i++) {
            int photo = i + 1;
            DeleteMessage deleteMessage = new DeleteMessage(fromId, msgId - photo);
            bot.execute(deleteMessage);
        }
    }


    private List<SendMessage> sendErr(Update update) {
        return Collections.singletonList(new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resources.getErrors().getCommonError()));
    }

    private List<SendMessage> createMsgToUser(UserAccommodation userAccommodation) {
        String text1 = resources.getMsgs().getAdmin().getSuccessApprovedForUser();
        String text2 = resources.getMsgs().getAdmin().getSuccessApprovedForUser2()
                + " " + config.getValidateData().getChannelURL();
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
        long channelId = config.getValidateData().getChannelID();
        if (userAccommodation.getPhotos().size() == 1) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto(new InputFile(userAccommodation.getPhotos().get(0)));
            sendPhoto.setChatId(channelId);
            sendPhoto.setCaption(userAccommodation.getDescription());
            bot.execute(sendPhoto);
            logger.writeInfo("sale send to channel " + channelId + " with 1 photo successfully", this.getClass());
        } else {
            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setMedias(getMedias(userAccommodation));
            sendMediaGroup.setChatId(channelId);
            bot.execute(sendMediaGroup);
            logger.writeInfo("sale send to channel " + channelId + " with many photo successfully", this.getClass());
        }
    }

    private void createAndSendSearchToChannel(UserAccommodation userAccommodation) throws Exception {
        long channelID = config.getValidateData().getChannelID();
        if (userAccommodation.getPhotos() == null || userAccommodation.getPhotos().size() == 0) {
            logger.writeInfo("no photo in accommodation #" + userAccommodation.getId(), this.getClass());
            SendMessage sendMessage = new SendMessage(String.valueOf(channelID),
                    userAccommodation.getDescription());
            bot.execute(sendMessage);
            logger.writeInfo("search send to channel " + channelID + " without photo successfully", this.getClass());
            return;
        }
        if (userAccommodation.getPhotos().size() == 1) {
            logger.writeInfo("find one photo in accommodation #" + userAccommodation.getId(), this.getClass());
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto(new InputFile(userAccommodation.getPhotos().get(0)));
            sendPhoto.setChatId(channelID);
            sendPhoto.setCaption(userAccommodation.getDescription());
            bot.execute(sendPhoto);
            logger.writeInfo("search send to channel " + channelID + " with 1 photo successfully", this.getClass());
        } else {
            logger.writeInfo("find more photo in accommodation #" + userAccommodation.getId(), this.getClass());
            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setMedias(getMedias(userAccommodation));
            sendMediaGroup.setChatId(channelID);
            bot.execute(sendMediaGroup);
            logger.writeInfo("search send to channel " + channelID + " with many photo successfully", this.getClass());
        }
    }

    private void updateUserAccommodation(UserAccommodation userAccommodation) {
        userAccommodation.setApproved(true);
        userAccommodation.setTopical(false);
        userAccommodation.setCreatedDate(new Date());
        accommodationStorage.saveAccommodation(userAccommodation);
        logger.writeInfo("accommodation #" + userAccommodation.getId() + " was updated", this.getClass());
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


