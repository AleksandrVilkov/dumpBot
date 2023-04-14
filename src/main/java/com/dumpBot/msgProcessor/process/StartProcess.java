package com.dumpBot.msgProcessor.process;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartProcess extends BaseProcess implements Process {
    @Override
    public SendMessage execute(Update update) {
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getFrom().getId()), "StartProcess");
        return sendMessage;
    }
}
