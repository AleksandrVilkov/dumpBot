package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.common.Util;
import com.dumpBot.model.Action;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.ResourcesHelper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartRegistrationProcess extends BaseProcess implements MsgProcess {
    @Override
    public SendMessage execute(Update update, ResourcesHelper resourcesHelper) {
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getWelcomeMessage());
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        Callback callback = new Callback(userId, Action.REGISTRATION_ACTION);
        String token = saveTempWithToken(callback, resourcesHelper);
        sendMessage.setReplyMarkup(resourcesHelper.createRegistrationButton(userId, token));
        return sendMessage;
    }

    private String saveTempWithToken(Callback callback, ResourcesHelper resourcesHelper) {
        String token = Util.newMd5FromCalBack(callback);
        resourcesHelper.getStorage().saveTempData(token, callback);
        return token;
    }

}
