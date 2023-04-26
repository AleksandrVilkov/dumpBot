package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.loger.ILogger;
import com.dumpBot.processor.IStorage;
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
    @Autowired
    IStorage storage;
    public RegistrationProcess() {
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
        SendMessage sendMessage = new SendMessage(userId, resourcesHelper.getResources().getMsgs().getWelcomeRegistered());

        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboard = ReplyKeyboardMarkup.builder();
        List<KeyboardRow> buttons = new ArrayList<>();
        //TODO убрать в ресурсы
        String url = "https://taupe-bienenstitch-397031.netlify.app/";
        KeyboardButton registration = new KeyboardButton(resourcesHelper.getResources().getButtonsText().getSearchRequest());
        registration.setWebApp(new WebAppInfo(url + "registration"));
        buttons.add(new KeyboardRow(Collections.singletonList(registration)));
        keyboard.keyboard(buttons);
        keyboard.resizeKeyboard(true);
        ReplyKeyboardMarkup inlineKeyboardMarkup = keyboard.build();
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
}
