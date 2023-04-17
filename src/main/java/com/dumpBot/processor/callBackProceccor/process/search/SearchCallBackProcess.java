package com.dumpBot.processor.callBackProceccor.process.search;

import com.dumpBot.model.Callback;
import com.dumpBot.processor.ResourcesHelper;
import com.dumpBot.processor.callBackProceccor.process.CallBackProcess;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SearchCallBackProcess implements CallBackProcess {
    @Override
    public SendMessage execute(Update update, ResourcesHelper buttonMaker,  Callback callback) {
        return null;
    }
}
