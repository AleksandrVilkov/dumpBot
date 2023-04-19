package com.dumpBot.processor.msgProcessor.process;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DefaultProcess implements MsgProcess {
    @Override
    public SendMessage execute(Update update) {
        return null;
    }
}
