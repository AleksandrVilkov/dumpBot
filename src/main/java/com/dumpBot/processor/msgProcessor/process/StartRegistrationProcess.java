package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.processor.ResourcesHelper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartRegistrationProcess extends BaseProcess implements MsgProcess {
    @Override
    public SendMessage execute(Update update, ResourcesHelper buttonMaker) {
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getFrom().getId()),
                buttonMaker.getResources().getMsgs().getWelcomeMessage());
        sendMessage.setReplyMarkup(buttonMaker.createRegistrationButton(String.valueOf(update.getMessage().getFrom().getId())));
        return sendMessage;
    }
}
