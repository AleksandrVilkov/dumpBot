package com.dumpBot.processor.msgProcessor.process;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MsgProcess {

    void processResultPreviousStep();

    void preparationCurrentProcess();

    SendMessage execute(Update update);
}
