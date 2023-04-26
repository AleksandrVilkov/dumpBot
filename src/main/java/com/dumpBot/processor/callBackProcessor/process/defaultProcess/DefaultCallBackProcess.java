package com.dumpBot.processor.callBackProcessor.process.defaultProcess;

import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.callBackProcessor.process.CallBackProcess;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DefaultCallBackProcess implements CallBackProcess {
    @Override
    public SendMessage execute(Update update, Callback callback) {
        return null;
    }
}
