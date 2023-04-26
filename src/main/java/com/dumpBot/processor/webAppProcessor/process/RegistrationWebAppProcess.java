package com.dumpBot.processor.webAppProcessor.process;

import com.dumpBot.model.WebAppData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class RegistrationWebAppProcess implements WebAppProcess {


    @Override
    public void processData(Update update, WebAppData webAppData) {

    }

    @Override
    public List<SendMessage> prepareAnswer(Update update) {
        return null;
    }
}
