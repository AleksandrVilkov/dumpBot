package com.dumpBot.processor.msgProcessor.process.admin;

import com.dumpBot.loger.ILogger;
import com.dumpBot.model.User;
import com.dumpBot.model.enums.Action;
import com.dumpBot.model.enums.Role;
import com.dumpBot.processor.msgProcessor.process.BaseMsgProcess;
import com.dumpBot.processor.msgProcessor.MsgProcess;
import com.dumpBot.processor.msgProcessor.process.admin.handlers.TextMsgHandler;
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
public class MainAdminProcess extends BaseMsgProcess implements MsgProcess {
    @Autowired
    IUserStorage userStorage;
    @Autowired
    ILogger logger;

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
        logger.writeInfo("Start main admin process for " + user.getLogin() + " " + user.getUserName());
        String adminId = user.getLogin();
        if (!user.getRole().equals(Role.ADMIN_ROLE)) {
            logger.writeWarning("user " + user.getLogin() + " " + user.getUserName() + "is not ADMIN!");
            return sendErr(adminId);
        }

        //Если находимся в режиме ожидания - то идем в обработчик текстовых сообщений
        if (user.isWaitingMessages()) {
            logger.writeInfo("see on text message...");
            TextMsgHandler msgHandler = HandlerFactory.getHandler(update.getMessage());
            return msgHandler.execute(update.getMessage());
        }
        logger.writeInfo("creating main admin menu..");
        SendMessage helloMsg = new SendMessage(adminId, "Привет, @" + user.getUserName() + "!");
        SendMessage callToActionMsg = new SendMessage(adminId, "Выбери действие, которое ты хочешь совершить. " +
                "Помни, люди на тебя надеятся." +
                "от тебя зависит успех продажи их говна!");
        callToActionMsg.setReplyMarkup(createKeyBoard());
        List<SendMessage> res = new ArrayList<>();
        res.add(helloMsg);
        res.add(callToActionMsg);
        user.setClientAction(Action.ADMINISTRATION.name());
        user.setWaitingMessages(true);
        userStorage.saveUser(user);
        logger.writeInfo("admin menu was create. User " + user.getLogin() + " " + user.getUserName() + "will be updated");
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
