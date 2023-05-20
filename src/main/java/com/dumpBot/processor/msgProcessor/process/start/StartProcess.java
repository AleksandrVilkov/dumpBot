package com.dumpBot.processor.msgProcessor.process.start;

import com.dumpBot.config.Config;
import com.dumpBot.loger.ILogger;
import com.dumpBot.processor.msgProcessor.process.BaseMsgProcess;
import com.dumpBot.processor.msgProcessor.MsgProcess;
import com.dumpBot.resources.Resources;
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
public class StartProcess extends BaseMsgProcess implements MsgProcess {
    @Autowired
    ILogger logger;
    @Autowired
    Resources resources;
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
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start main menu process for user " + userId);

        SendMessage sendMessage = new SendMessage(userId,
                resources.getMsgs().getWelcome());

        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboard = ReplyKeyboardMarkup.builder();

        List<KeyboardRow> buttons = new ArrayList<>();

        KeyboardButton search = new KeyboardButton(resources.getButtonsText().getSearchRequest());
        search.setWebApp(new WebAppInfo(config.getWebApp().getUrl() + config.getWebApp().getPathSearch()));
        buttons.add(new KeyboardRow(Collections.singletonList(search)));

        KeyboardButton sale = new KeyboardButton(resources.getButtonsText().getPlaceAnAd());

        String saleUrl = config.getWebApp().getUrl() + config.getWebApp().getPathSale();
        sale.setWebApp(new WebAppInfo(saleUrl));
        buttons.add(new KeyboardRow(Collections.singletonList(sale)));

        KeyboardButton rules = new KeyboardButton(resources.getButtonsText().getRules());
        rules.setWebApp(new WebAppInfo(config.getWebApp().getUrl() + "/rules"));
        buttons.add(new KeyboardRow(Collections.singletonList(rules)));

        keyboard.keyboard(buttons);
        keyboard.resizeKeyboard(true);

        ReplyKeyboardMarkup inlineKeyboardMarkup = keyboard.build();

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return Collections.singletonList(sendMessage);
    }
}
