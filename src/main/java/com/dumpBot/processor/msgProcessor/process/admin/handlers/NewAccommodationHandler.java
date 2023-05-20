package com.dumpBot.processor.msgProcessor.process.admin.handlers;

import com.dumpBot.bot.Bot;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.UserAccommodation;
import com.dumpBot.storage.IAccommodationStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewAccommodationHandler implements TextMsgHandler {
    @Autowired
    IAccommodationStorage accommodationStorage;
    @Autowired
    ILogger logger;
    @Autowired
    Bot bot;

    //TODO убрать в ресурсы, отрефакторить
    @Override
    public List<SendMessage> execute(Message message) {
        List<UserAccommodation> inconsistentAccommodation = accommodationStorage.getAllInconsistent();
        List<SendMessage> result = new ArrayList<>();
        result.add(new SendMessage(String.valueOf(message.getFrom().getId()), "Всего не рассмотренных запросов: " + inconsistentAccommodation.size()));
        //TODO использовать объект для колбека
        for (UserAccommodation userAccommodation : inconsistentAccommodation) {
                try {
                    createAndSend(userAccommodation, String.valueOf(message.getFrom().getId()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
        return result;
    }


    private InlineKeyboardMarkup createInlineKeyboardMarkup(UserAccommodation userAccommodation) {
        InlineKeyboardButton a = new InlineKeyboardButton();
        a.setCallbackData("{\"accommodationId\":" + userAccommodation.getId() + ", " + "\"result\": \"approved\"}");
        a.setText("Одобрить");

        InlineKeyboardButton b = new InlineKeyboardButton();
        b.setCallbackData("{\"accommodationId\":" + userAccommodation.getId() + ", " + "\"result\": \"rejected\"}");
        b.setText("Отклонить");

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> kr = new ArrayList<>();
        kr.add(a);
        kr.add(b);
        keyboard.add(kr);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    private void createAndSend(UserAccommodation userAccommodation, String chatId) throws Exception {
        if (userAccommodation.getPhotos() == null || userAccommodation.getPhotos().size() == 0) {
            logger.writeInfo("no photo in user callback");
            SendMessage sendMessage = new SendMessage(chatId, userAccommodation.getDescription());
            sendMessage.setReplyMarkup(createInlineKeyboardMarkup(userAccommodation));
            bot.execute(sendMessage);
            return;
        }
        if (userAccommodation.getPhotos().size() == 1) {
            logger.writeInfo("find one photo in callback");
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto(new InputFile(userAccommodation.getPhotos().get(0)));
            sendPhoto.setChatId(chatId);
            sendPhoto.setCaption(userAccommodation.getDescription());
            sendPhoto.setReplyMarkup(createInlineKeyboardMarkup(userAccommodation));
            bot.execute(sendPhoto);
        } else {
            logger.writeInfo("find more photo in callback");
            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setMedias(getMedias(userAccommodation));
            sendMediaGroup.setChatId(chatId);
            bot.execute(sendMediaGroup);
        }
    }

    //TODO прекриплять кнопки к множеству фото. Найки костыль. Есть ошибка при редактировании сообщения с одной фото
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
