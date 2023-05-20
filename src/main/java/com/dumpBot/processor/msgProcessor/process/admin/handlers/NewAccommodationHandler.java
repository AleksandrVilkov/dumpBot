package com.dumpBot.processor.msgProcessor.process.admin.handlers;

import com.dumpBot.model.UserAccommodation;
import com.dumpBot.storage.IAccommodationStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewAccommodationHandler implements TextMsgHandler {
    @Autowired
    IAccommodationStorage accommodationStorage;

    //TODO убрать в ресурсы, отрефакторить
    @Override
    public List<SendMessage> execute(Message message) {
        List<UserAccommodation> inconsistentAccommodation = accommodationStorage.getAllInconsistent();
        List<SendMessage> result = new ArrayList<>();
        result.add(new SendMessage(String.valueOf(message.getFrom().getId()),
                "Всего не рассмотренных запросов: " + inconsistentAccommodation.size()));
        for (UserAccommodation userAccommodation : inconsistentAccommodation) {
            SendMessage sendMessage = new SendMessage(String.valueOf(message.getFrom().getId()), userAccommodation.getDescription());
            InlineKeyboardButton a = new InlineKeyboardButton();
            a.setCallbackData("{\"accommodationId\":" + userAccommodation.getId() +", "
                    + "\"result\": \"approved\"}");
            a.setText("Одобрить");

            InlineKeyboardButton b = new InlineKeyboardButton();
            b.setCallbackData("{\"accommodationId\":" + userAccommodation.getId() +", "
                    + "\"result\": \"rejected\"}");
            b.setText("Отклонить");

            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            List<InlineKeyboardButton> kr = new ArrayList<>();
            kr.add(a);
            kr.add(b);
            keyboard.add(kr);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            //TODO очищать клавиатуру с кнопками старыми
            result.add(sendMessage);
        }
        return result;
    }
}
