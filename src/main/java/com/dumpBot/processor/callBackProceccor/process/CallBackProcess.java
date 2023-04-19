package com.dumpBot.processor.callBackProceccor.process;

import com.dumpBot.model.callback.Callback;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallBackProcess {
    SendMessage execute(Update update, Callback callback);
}
