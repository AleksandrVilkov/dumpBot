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

import java.util.*;

@Component
public class StartProcess extends BaseMsgProcess implements MsgProcess {
    @Autowired
    ILogger logger;
    @Autowired
    IStorage storage;
    @Autowired
    ResourcesHelper resourcesHelper;

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
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start main menu process for user " + userId);

        SendMessage sendMessage = new SendMessage(userId,
                resourcesHelper.getResources().getMsgs().getWelcomeRegistered());

        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboard = ReplyKeyboardMarkup.builder();

        List<KeyboardRow> buttons = new ArrayList<>();

        //TODO убрать в ресурсы

        String url = "https://taupe-bienenstitch-397031.netlify.app/";

        KeyboardButton search = new KeyboardButton(resourcesHelper.getResources().getButtonsText().getSearchRequest());
        search.setWebApp(new WebAppInfo(url + "search"));
        buttons.add(new KeyboardRow(Collections.singletonList(search)));

        KeyboardButton sale = new KeyboardButton(resourcesHelper.getResources().getButtonsText().getPlaceAnAd());
        sale.setWebApp(new WebAppInfo(url + "sale"));
        buttons.add(new KeyboardRow(Collections.singletonList(sale)));

        KeyboardButton rules = new KeyboardButton(resourcesHelper.getResources().getButtonsText().getRules());
        rules.setWebApp(new WebAppInfo(url + "rules"));
        buttons.add(new KeyboardRow(Collections.singletonList(rules)));

        keyboard.keyboard(buttons);
        keyboard.resizeKeyboard(true);

        ReplyKeyboardMarkup inlineKeyboardMarkup = keyboard.build();

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return Collections.singletonList(sendMessage);
    }
}
