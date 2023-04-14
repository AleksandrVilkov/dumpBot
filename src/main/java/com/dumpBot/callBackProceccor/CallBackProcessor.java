package com.dumpBot.callBackProceccor;

import com.dumpBot.bot.ICallBackProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class CallBackProcessor implements ICallBackProcessor {
    @Override
    public SendMessage startCallbackProcessor() {
        return null;
    }
}
