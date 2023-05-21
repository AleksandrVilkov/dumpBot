package com.dumpBot.processor.callbackProcessor;

import com.dumpBot.bot.IButtonCallbackProcessor;
import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.ButtonCallBack;
import com.dumpBot.model.UserAccommodation;
import com.dumpBot.model.enums.AccommodationAction;
import com.dumpBot.resources.Resources;
import com.dumpBot.storage.IAccommodationStorage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;

@Component
public class CallbackProcessor implements IButtonCallbackProcessor {
    @Autowired
    ILogger logger;
    @Autowired
    IAccommodationStorage accommodationStorage;
    @Autowired
    Resources resources;

    @Override
    public List<SendMessage> startButtonCallbackProcessor(Update update) {
        logger.writeInfo("start button callback processor");
        ButtonCallBack buttonCallBack;
        try {
            buttonCallBack = Util.readButtonCallBack(update.getCallbackQuery().getData());
        } catch (JsonProcessingException e) {
            logger.writeStackTrace(e);
            return Collections.singletonList(new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                    resources.getErrors().getCommonError()));
        }
        UserAccommodation ua = accommodationStorage.getById(buttonCallBack.getAccommodationId());
        AccommodationAction action = Util.findEnumConstant(AccommodationAction.class, buttonCallBack.getResult());
        CallbackProcess callbackProcess = CallbackProcessFactory.getProcess(action);
        return callbackProcess.start(ua, update);
    }
}
