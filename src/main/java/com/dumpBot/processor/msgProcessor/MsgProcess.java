package com.dumpBot.processor.msgProcessor;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface MsgProcess {

    void processResultPreviousStep();

    void preparationCurrentProcess();

    List<SendMessage> execute(Update update);
}
