package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.processor.ResourcesHelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class DefaultProcess implements MsgProcess {
    @Override
    public SendMessage execute(Update update, ResourcesHelper buttonMaker) {
        return null;
    }
}
