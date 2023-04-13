package com.dumpBot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface IMessageProcessor {
    SendMessage startMessageProcessor();
}
