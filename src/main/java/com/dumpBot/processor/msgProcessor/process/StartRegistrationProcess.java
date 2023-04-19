package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.Action;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.IStorage;
import com.dumpBot.processor.ResourcesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartRegistrationProcess extends BaseProcess implements MsgProcess {
    @Autowired
    ResourcesHelper resourcesHelper;

    @Autowired
    ILogger logger;

    @Autowired
    IStorage storage;

    @Override
    public SendMessage execute(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start registration process for user " + userId);
        SendMessage sendMessage = new SendMessage(userId, resourcesHelper.getResources().getMsgs().getWelcomeMessage());

        Callback callback = new Callback(userId, Action.REGISTRATION_ACTION);
        String token = saveTempWithToken(callback);
        sendMessage.setReplyMarkup(resourcesHelper.createRegistrationButton(userId, token));
        return sendMessage;
    }

    private String saveTempWithToken(Callback callback) {
        String token = Util.newMd5FromCalBack(callback);
        storage.saveTempData(token, callback);
        return token;
    }

}
