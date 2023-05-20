package com.dumpBot.processor.callbackProcessor.process;

import com.dumpBot.model.ButtonCallBack;
import com.dumpBot.model.UserAccommodation;
import com.dumpBot.processor.callbackProcessor.CallbackProcess;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class RejectedProcess implements CallbackProcess {
    @Override
    public List<SendMessage> start(UserAccommodation userAccommodation, Update update) {
        return null;
    }
}
