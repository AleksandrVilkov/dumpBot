package com.dumpBot.processor.webAppProcessor.process;

import com.dumpBot.model.LastCallback;
import com.dumpBot.model.User;
import com.dumpBot.model.WebAppData;
import com.dumpBot.processor.IUserStorage;
import com.dumpBot.resources.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SearchWebAppProcess implements WebAppProcess {

    @Autowired
    IUserStorage storage;
    @Autowired
    Resources resources;

    @Override
    public boolean processData(Update update, WebAppData wp) {
        saveData(update, wp);
        return true;

    }

    @Override
    public List<SendMessage> prepareAnswer(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        List<SendMessage> result = new ArrayList<>();
        result.add(createKeyboardRemoveMsg(userId));
        result.add(createResponse(userId));
        return result;
    }


    private SendMessage createKeyboardRemoveMsg(String userId) {
        SendMessage sm = new SendMessage(userId, resources.getSuccess().getExcellent());
        sm.setReplyMarkup(new ReplyKeyboardRemove(true));
        return sm;
    }

    private void saveData(Update update, WebAppData wp) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        User user = storage.getUser(userId);
        user.setClientAction(wp.getAction());
        user.setWaitingMessages(true);
        LastCallback lastCallback = new LastCallback();
        lastCallback.setDescription(wp.getDescription());
        if (wp.getPrice() != null) {
            lastCallback.setPrice(wp.getPrice());
        }
        lastCallback.setCarId(wp.getCarId());
        user.setLastCallback(lastCallback.toString());
        storage.saveUser(user);
    }

    private SendMessage createResponse(String userId) {
        SendMessage sm = new SendMessage(userId, resources.getMsgs().getPhoto().getWithOrWithoutPhoto());
        sm.setReplyMarkup(makeKeyboard());
        return sm;
    }

    private ReplyKeyboardMarkup makeKeyboard() {
        List<KeyboardRow> buttons = new ArrayList<>();
        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboard = ReplyKeyboardMarkup.builder();
        KeyboardButton search = new KeyboardButton(resources.getButtonsText().getWithoutPhoto());
        buttons.add(new KeyboardRow(Collections.singletonList(search)));
        keyboard.keyboard(buttons);
        keyboard.resizeKeyboard(true);
        return keyboard.build();
    }

}
