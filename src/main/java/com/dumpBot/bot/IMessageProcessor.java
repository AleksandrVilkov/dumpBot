package com.dumpBot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface IMessageProcessor {
    SendMessage startMessageProcessor(Update update);
    SendMessage createError(Update update);
    SendMessage createErrAuthMsg(Update update);
}
