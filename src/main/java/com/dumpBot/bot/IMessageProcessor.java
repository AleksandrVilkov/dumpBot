package com.dumpBot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface IMessageProcessor {
    SendMessage startMessageProcessor();
    SendMessage createError();
    SendMessage createErrAuthMsg();
}
