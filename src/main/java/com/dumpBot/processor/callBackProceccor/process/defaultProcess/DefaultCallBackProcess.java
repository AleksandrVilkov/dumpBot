package com.dumpBot.processor.callBackProceccor.process.defaultProcess;

import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.callBackProceccor.process.CallBackProcess;
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
