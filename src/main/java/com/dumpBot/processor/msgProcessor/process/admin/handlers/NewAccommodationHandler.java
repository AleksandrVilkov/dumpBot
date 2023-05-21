package com.dumpBot.processor.msgProcessor.process.admin.handlers;

import com.dumpBot.bot.Bot;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.ButtonCallBack;
import com.dumpBot.model.UserAccommodation;
import com.dumpBot.model.enums.AccommodationAction;
import com.dumpBot.resources.Resources;
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
    @Autowired
    Resources resources;

    @Override
    public List<SendMessage> execute(Message message) {
        List<UserAccommodation> inconsistentAccommodation = accommodationStorage.getAllInconsistent();
        List<SendMessage> result = new ArrayList<>();
        result.add(new SendMessage(String.valueOf(message.getFrom().getId()),
                resources.getMsgs().getAdmin().getTotalRequests() + ": " + inconsistentAccommodation.size()));
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
        ButtonCallBack approveCallBack = new ButtonCallBack(userAccommodation.getId(), AccommodationAction.APPROVED.name());
        a.setCallbackData(approveCallBack.toString());
        a.setText(resources.getButtonsText().getApproved());

        InlineKeyboardButton b = new InlineKeyboardButton();
        ButtonCallBack rejectedCallBack = new ButtonCallBack(userAccommodation.getId(), AccommodationAction.REJECTED.name());
        b.setCallbackData(rejectedCallBack.toString());
        b.setText(resources.getButtonsText().getRejected());

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
            logger.writeInfo("no photo in user callback", this.getClass());
            SendMessage sendMessage = new SendMessage(chatId, userAccommodation.getDescription());
            sendMessage.setReplyMarkup(createInlineKeyboardMarkup(userAccommodation));
            bot.execute(sendMessage);
            return;
        }
        if (userAccommodation.getPhotos().size() == 1) {
            logger.writeInfo("find one photo in callback", this.getClass());
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto(new InputFile(userAccommodation.getPhotos().get(0)));
            sendPhoto.setChatId(chatId);
            sendPhoto.setCaption(userAccommodation.getDescription());
            sendPhoto.setReplyMarkup(createInlineKeyboardMarkup(userAccommodation));
            bot.execute(sendPhoto);
        } else {
            logger.writeInfo("find more photo in callback", this.getClass());
            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setMedias(getMedias(userAccommodation));
            sendMediaGroup.setChatId(chatId);
            bot.execute(sendMediaGroup);
            SendMessage sendMessage = new SendMessage(chatId, userAccommodation.getDescription());
            sendMessage.setReplyMarkup(createInlineKeyboardMarkup(userAccommodation));
            bot.execute(sendMessage);
        }
    }

    private List<InputMedia> getMedias(UserAccommodation userAccommodation) {
        List<InputMedia> inputMedia = new ArrayList<>();
        boolean isFirst = true;
        for (String photo : userAccommodation.getPhotos()) {
            InputMediaPhoto inputMediaPhoto = new InputMediaPhoto(photo);
            if (isFirst) {
                isFirst = false;
            }
            inputMedia.add(inputMediaPhoto);
        }
        return inputMedia;
    }
}
