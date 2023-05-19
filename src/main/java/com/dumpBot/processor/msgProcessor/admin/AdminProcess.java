package com.dumpBot.processor.msgProcessor.admin;

import com.dumpBot.model.User;
import com.dumpBot.model.enums.Action;
import com.dumpBot.model.enums.Role;
import com.dumpBot.processor.msgProcessor.process.BaseMsgProcess;
import com.dumpBot.processor.msgProcessor.process.MsgProcess;
import com.dumpBot.storage.IUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AdminProcess extends BaseMsgProcess implements MsgProcess {
    @Autowired
    IUserStorage userStorage;

    @Override
    public void processResultPreviousStep() {

    }

    @Override
    public void preparationCurrentProcess() {

    }

    //TODO убрать текст в проперти файл
    @Override
    public List<SendMessage> execute(Update update) {
        User user = userStorage.getUser(String.valueOf(update.getMessage().getFrom().getId()));
        String adminId = user.getLogin();

        if (!user.getRole().equals(Role.ADMIN_ROLE)) {
           return sendErr(adminId);
        }

        SendMessage helloMsg = new SendMessage(adminId, "Привет, @" + user.getUserName() + "!");
        SendMessage callToActionMsg = new SendMessage(adminId, "Выбери действие, которое ты хочешь совершить. " +
                "Помни, люди на тебя надеятся." +
                "от тебя зависит успех продажи их говна!");
        callToActionMsg.setReplyMarkup(createKeyBoard());

        List<SendMessage> res = new ArrayList<>();
        res.add(helloMsg);
        res.add(callToActionMsg);
        user.setClientAction(Action.ADMINISTRATION.name());
        userStorage.saveUser(user);
        return res;
    }

    private List<SendMessage> sendErr(String id) {
        SendMessage f = new SendMessage(id, "Что то мне кажется, что ты не похож(а) на админа!");
        SendMessage s = new SendMessage(id, "А ну быстро свалил отсуда, петушара!");
        List<SendMessage> res = new ArrayList<>();
        res.add(f);
        res.add(s);
        return res;
    }
    private ReplyKeyboardMarkup createKeyBoard() {
        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboard = ReplyKeyboardMarkup.builder();
        List<KeyboardRow> buttons = new ArrayList<>();
        KeyboardButton newAnnouncements = new KeyboardButton("Новые запросы");
        KeyboardButton statistics = new KeyboardButton("Статистика");
        buttons.add(new KeyboardRow(Collections.singletonList(newAnnouncements)));
        buttons.add(new KeyboardRow(Collections.singletonList(statistics)));
        keyboard.keyboard(buttons);
        keyboard.resizeKeyboard(true);
        return keyboard.build();
    }
}
