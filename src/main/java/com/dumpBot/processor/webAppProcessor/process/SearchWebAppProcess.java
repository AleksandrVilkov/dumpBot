package com.dumpBot.processor.webAppProcessor.process;

import com.dumpBot.model.User;
import com.dumpBot.model.WebAppData;
import com.dumpBot.processor.IUserStorage;
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
        //TODO Убрать в ресурсы
        SendMessage sm = new SendMessage(userId, "Отлично!");
        sm.setReplyMarkup(new ReplyKeyboardRemove(true));
        return sm;
    }

    private void saveData(Update update, WebAppData wp) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        String data = update.getMessage().getWebAppData().getData();
        User user = storage.getUser(userId);
        user.setClientAction(wp.getAction());
        user.setWaitingMessages(true);
        user.setLastCallback(data);
        storage.saveUser(user);
    }

    private SendMessage createResponse(String userId) {
        //TODO вынести текст в ресурсы
        SendMessage sm = new SendMessage(userId, "Если есть фото - отправь их мне. Если нет - то нажми на кнопку без фото.");
        sm.setReplyMarkup(makeKeyboard());
        return sm;
    }

    private ReplyKeyboardMarkup makeKeyboard() {
        List<KeyboardRow> buttons = new ArrayList<>();
        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboard = ReplyKeyboardMarkup.builder();
        KeyboardButton search = new KeyboardButton("Без фото");
        buttons.add(new KeyboardRow(Collections.singletonList(search)));

        keyboard.keyboard(buttons);
        return keyboard.build();
    }

}
