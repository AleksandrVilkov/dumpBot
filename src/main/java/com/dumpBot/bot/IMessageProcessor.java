package com.dumpBot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface IMessageProcessor {
   List<SendMessage> startMessageProcessor(Update update);
    SendMessage createError(Update update);
    SendMessage createErrAuthMsg(Update update);
}
