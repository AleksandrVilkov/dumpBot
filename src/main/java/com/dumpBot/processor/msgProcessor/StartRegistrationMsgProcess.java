package com.dumpBot.processor.msgProcessor;

import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.enums.Action;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.IStorage;
import com.dumpBot.processor.ResourcesHelper;
import com.dumpBot.processor.msgProcessor.process.BaseMsgProcess;
import com.dumpBot.processor.msgProcessor.process.MsgProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartRegistrationMsgProcess extends BaseMsgProcess implements MsgProcess {
    @Autowired
    ResourcesHelper resourcesHelper;
    @Autowired
    ILogger logger;
    @Autowired
    IStorage storage;
    public StartRegistrationMsgProcess() {
    }

    @Override
    public void processResultPreviousStep() {
        //no use
    }

    @Override
    public void preparationCurrentProcess() {
        //no use
    }

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
