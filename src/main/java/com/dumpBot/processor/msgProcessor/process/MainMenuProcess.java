package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.model.Action;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.ResourcesHelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashMap;
import java.util.Map;

public class MainMenuProcess implements MsgProcess {
    @Override
    public SendMessage execute(Update update, ResourcesHelper resourcesHelper) {
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getWelcomeRegistered());
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        Map<String, String> data = new HashMap<>();
        Callback searchCallback = new Callback(userId, Action.SEARCH_REQUEST_ACTION);
        data.put(resourcesHelper.getResources().getButtonsText().getSearchRequest(), resourcesHelper.getButtonData("token"));
        Callback saleCallback = new Callback(userId, Action.SALE_ACTION);
        data.put(resourcesHelper.getResources().getButtonsText().getPlaceAnAd(), resourcesHelper.getButtonData("token"));
        Callback ruleCallback = new Callback(userId, Action.RULES_ACTION);
        data.put(resourcesHelper.getResources().getButtonsText().getRules(), resourcesHelper.getButtonData("token"));
        InlineKeyboardMarkup buttons = resourcesHelper.createInlineKeyBoard(data, 1);

        sendMessage.setReplyMarkup(buttons);
        return sendMessage;
    }
}
