package com.dumpBot.processor.msgProcessor.process.search;

import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.User;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.IStorage;
import com.dumpBot.processor.ResourcesHelper;
import com.dumpBot.processor.msgProcessor.process.BaseProcess;
import com.dumpBot.processor.msgProcessor.process.MsgProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SearchProcess extends BaseProcess implements MsgProcess {
    @Autowired
    IStorage storage;
    @Autowired
    ILogger logger;
    @Autowired
    ResourcesHelper resourcesHelper;

    @Override
    public void processResultPreviousStep() {

    }

    @Override
    public void preparationCurrentProcess() {

    }

    @Override
    public SendMessage execute(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        User user = storage.getUser(userId);
        String stringCallBack = user.getLastCallback();
        Callback callback = null;
        try {
            callback = Util.readCallBack(stringCallBack);
            logger.writeInfo("callback " + callback.toString() + " was found by user " + userId);
        } catch (Exception e) {
            logger.writeStackTrace(e);
        }
        if (callback != null) {
            callback.setDescription(update.getMessage().getText());
            user.setLastCallback(callback.toString());
            storage.saveUser(user);
            return new SendMessage(userId, resourcesHelper.getResources().getMsgs().getSearch().getAddPhoto());
        }
        return new SendMessage(userId, resourcesHelper.getResources().getErrors().getCommonError());
    }
}
