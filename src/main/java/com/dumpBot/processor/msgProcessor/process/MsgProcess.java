package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.processor.ResourcesHelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MsgProcess {
    SendMessage execute(Update update, ResourcesHelper buttonMaker);
}
