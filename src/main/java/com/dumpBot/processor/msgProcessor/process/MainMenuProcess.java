package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.processor.ResourcesHelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MainMenuProcess implements MsgProcess {
    @Override
    public SendMessage execute(Update update, ResourcesHelper buttonMaker) {
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getFrom().getId()),
                buttonMaker.getResources().getMsgs().getWelcomeRegistered());
        sendMessage.setReplyMarkup(buttonMaker.createMainButtons(String.valueOf(update.getMessage().getFrom().getId())));
        return sendMessage;
    }
}
