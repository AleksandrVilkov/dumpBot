package com.dumpBot.msgProcessor.process;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class DefaultProcess implements Process {
    @Override
    public SendMessage execute(Update update) {
        return null;
    }
}
