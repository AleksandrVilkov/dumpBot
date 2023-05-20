package com.dumpBot.processor.msgProcessor.process.admin.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface TextMsgHandler {
    List<SendMessage> execute(Message message);
}
