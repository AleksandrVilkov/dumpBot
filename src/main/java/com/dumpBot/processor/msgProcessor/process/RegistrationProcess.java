package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.config.Config;
import com.dumpBot.loger.ILogger;
import com.dumpBot.processor.IUserStorage;
import com.dumpBot.processor.ResourcesHelper;
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
public class RegistrationProcess extends BaseMsgProcess implements MsgProcess {
    @Autowired
    ResourcesHelper resourcesHelper;
    @Autowired
    ILogger logger;
    Config config;


    public RegistrationProcess() {
        this.config = Config.init();
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
    public List<SendMessage> execute(Update update) {
        List<SendMessage> result = new ArrayList<>();

        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start registration process for user " + userId);
        result.add(new SendMessage(userId, resourcesHelper.getResources().getMsgs().getRegistration().getHello()));
        result.add(new SendMessage(userId, resourcesHelper.getResources().getMsgs().getRegistration().getGo()));
        SendMessage sendMessage = new SendMessage(userId, resourcesHelper.getResources().getMsgs().getRegistration().getDescription());

        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboard = ReplyKeyboardMarkup.builder();
        List<KeyboardRow> buttons = new ArrayList<>();
        String url = config.getWebApp().getUrl();
        KeyboardButton registration = new KeyboardButton(resourcesHelper.getResources().getButtonsText().getRegistration());
        registration.setWebApp(new WebAppInfo(url + config.getWebApp().getPathRegistration()));
        buttons.add(new KeyboardRow(Collections.singletonList(registration)));
        keyboard.keyboard(buttons);
        keyboard.resizeKeyboard(true);
        ReplyKeyboardMarkup inlineKeyboardMarkup = keyboard.build();
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        result.add(sendMessage);
        result.add(new SendMessage(userId, resourcesHelper.getResources().getMsgs().getRegistration().getTapRegistration()));

        return result;
    }
}
