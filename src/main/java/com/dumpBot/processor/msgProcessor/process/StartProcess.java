package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.Action;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.IStorage;
import com.dumpBot.processor.ResourcesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashMap;
import java.util.Map;

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

        Map<String, String> data = new HashMap<>();

        Callback searchCallback = new Callback(userId, Action.SEARCH_REQUEST_ACTION);
        String tokenSearchCallback = saveTempWithToken(searchCallback);
        data.put(resourcesHelper.getResources().getButtonsText().getSearchRequest(),
                resourcesHelper.getButtonData(tokenSearchCallback));

        Callback saleCallback = new Callback(userId, Action.SALE_ACTION);
        String tokenSaleCallback = saveTempWithToken(saleCallback);
        data.put(resourcesHelper.getResources().getButtonsText().getPlaceAnAd(),
                resourcesHelper.getButtonData(tokenSaleCallback));

        Callback ruleCallback = new Callback(userId, Action.RULES_ACTION);
        String tokenRuleCallback = saveTempWithToken(ruleCallback);

        data.put(resourcesHelper.getResources().getButtonsText().getRules(),
                resourcesHelper.getButtonData(tokenRuleCallback));

        InlineKeyboardMarkup buttons = resourcesHelper.createInlineKeyBoard(data, 1);

        sendMessage.setReplyMarkup(buttons);
        return sendMessage;
    }

    private String saveTempWithToken(Callback callback) {
        String token = Util.newMd5FromCalBack(callback);
        storage.saveTempData(token, callback);
        return token;
    }
}
