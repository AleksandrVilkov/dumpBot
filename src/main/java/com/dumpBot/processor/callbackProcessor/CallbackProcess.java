package com.dumpBot.processor.callbackProcessor;

import com.dumpBot.model.ButtonCallBack;
import com.dumpBot.model.UserAccommodation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface CallbackProcess {
    List<SendMessage> start(UserAccommodation userAccommodation, Update update);
}
