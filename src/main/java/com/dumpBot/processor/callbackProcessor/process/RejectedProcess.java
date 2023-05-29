package com.dumpBot.processor.callbackProcessor.process;

import com.dumpBot.model.User;
import com.dumpBot.model.UserAccommodation;
import com.dumpBot.processor.callbackProcessor.CallbackProcess;
import com.dumpBot.storage.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;

@Component
public class RejectedProcess implements CallbackProcess {
    @Autowired
    UserStorage userStorage;

    @Override
    public List<SendMessage> start(UserAccommodation userAccommodation, Update update) {
        //TODO перенести в ресурсы
        String text = "Укажи причину отклонения запроса. Обрати внимание, это сообщение получит автор, и все остальные администраторы.";
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()), text);
        sendMessage.setReplyToMessageId(update.getCallbackQuery().getMessage().getMessageId());
        User user = userStorage.getUser(String.valueOf(update.getCallbackQuery().getFrom().getId()));
        user.setClientAction("REJECTED");
        user.setLastCallback(update.getCallbackQuery().getData());
        userStorage.saveUser(user);
        return Collections.singletonList(sendMessage);
    }
}
