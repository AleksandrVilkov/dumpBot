package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.loger.ILogger;
import com.dumpBot.processor.IStorage;
import com.dumpBot.processor.ResourcesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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
    public SendMessage execute(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start main menu process for user " + userId);

        SendMessage sendMessage = new SendMessage(userId,
                resourcesHelper.getResources().getMsgs().getWelcomeRegistered());

        InlineKeyboardMarkup.InlineKeyboardMarkupBuilder inlineKeyboardMarkupBuilder = InlineKeyboardMarkup.builder();

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        //TODO убрать в ресурсы

        String url = "https://taupe-bienenstitch-397031.netlify.app/";

        InlineKeyboardButton search = new InlineKeyboardButton(resourcesHelper.getResources().getButtonsText().getSearchRequest());
        search.setWebApp(new WebAppInfo(url + "search"));
        buttons.add(Collections.singletonList(search));

        InlineKeyboardButton sale = new InlineKeyboardButton(resourcesHelper.getResources().getButtonsText().getPlaceAnAd());
        sale.setWebApp(new WebAppInfo(url + "sale"));
        buttons.add(Collections.singletonList(sale));

        InlineKeyboardButton rules = new InlineKeyboardButton(resourcesHelper.getResources().getButtonsText().getRules());
        rules.setWebApp(new WebAppInfo(url + "rules"));
        buttons.add(Collections.singletonList(rules));

        inlineKeyboardMarkupBuilder.keyboard(buttons);

        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardMarkupBuilder.build();

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
}
