package com.dumpBot.processor.msgProcessor.process.registration;

import com.dumpBot.config.Config;
import com.dumpBot.loger.ILogger;
import com.dumpBot.processor.msgProcessor.process.BaseMsgProcess;
import com.dumpBot.processor.msgProcessor.MsgProcess;
import com.dumpBot.resources.Resources;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@NoArgsConstructor
public class RegistrationProcess extends BaseMsgProcess implements MsgProcess {
    @Autowired
    Resources resources;
    @Autowired
    ILogger logger;
    @Autowired
    Config config;

    @Override
    public void processResultPreviousStep() {
        //no use
    }

    @Override
    public void preparationCurrentProcess() {
        //no use
    }

    @Override
    public List<SendMessage> execute(Update update) {
        List<SendMessage> result = new ArrayList<>();
        String userId = String.valueOf(update.getMessage().getFrom().getId());

        if (update.getMessage().getFrom().getUserName() == null) {
            return returnErrNullUserName(userId);
        }

        logger.writeInfo("start registration process for user " + userId, this.getClass());
        result.add(new SendMessage(userId, resources.getMsgs().getRegistration().getHello()));
        result.add(new SendMessage(userId, resources.getMsgs().getRegistration().getGo()));
        SendMessage sendMessage = new SendMessage(userId, resources.getMsgs().getRegistration().getDescription());

        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboard = ReplyKeyboardMarkup.builder();
        List<KeyboardRow> buttons = new ArrayList<>();
        String url = config.getWebApp().getUrl();
        KeyboardButton registration = new KeyboardButton(resources.getButtonsText().getRegistration());
        registration.setWebApp(new WebAppInfo(url + config.getWebApp().getPathRegistration()));
        buttons.add(new KeyboardRow(Collections.singletonList(registration)));
        keyboard.keyboard(buttons);
        keyboard.resizeKeyboard(true);
        ReplyKeyboardMarkup inlineKeyboardMarkup = keyboard.build();
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        result.add(sendMessage);
        result.add(new SendMessage(userId, resources.getMsgs().getRegistration().getTapRegistration()));

        return result;
    }

    private List<SendMessage> returnErrNullUserName(String userId) {
        SendMessage sendMessage = new SendMessage(userId, resources.getErrors().getUserNameErr());
        return Collections.singletonList(sendMessage);
    }
}
