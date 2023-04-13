package com.dumpBot.msgProcessor;

import com.dumpBot.bot.IMessageProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class MessageProcessor implements IMessageProcessor {
    @Override
    public SendMessage startMessageProcessor() {
        return null;
    }

    @Override
    public SendMessage createError() {
        return null;
    }

    @Override
    public SendMessage createErrAuthMsg() {
        return null;
    }
}
