package com.dumpBot.processor.webAppProcessor.process;

import com.dumpBot.model.WebAppData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface WebAppProcess {

    boolean processData(Update update, WebAppData webAppData);
    List<SendMessage> prepareAnswer(Update update);

}
