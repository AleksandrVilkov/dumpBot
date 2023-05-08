package com.dumpBot.processor.msgProcessor.process.ready;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface IReadyProcess {
    List<SendMessage> execute(Update update);
}
