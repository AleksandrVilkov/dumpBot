package com.dumpBot.buttonMaker;

import com.dumpBot.common.Util;
import com.dumpBot.config.Config;
import com.dumpBot.model.Action;
import com.dumpBot.model.Callback;
import com.dumpBot.model.CallbackSubsection;
import com.dumpBot.resources.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ButtonMaker {
    Resources resources;

    @Autowired
    IStorage storage;


    public ButtonMaker() {
      this.resources = Resources.init();
    }

    public InlineKeyboardMarkup createRegistrationButton(String userId) {
        Map<String, String> data = new HashMap<>();
        Callback callback = new Callback(userId, Action.REGISTRATION_ACTION);
        data.put(resources.getButtonsText().getRegistration(), getButtonData(saveTempWithToken(callback)));
        return createInlineKeyBoard(data, 1);

    }

    public InlineKeyboardMarkup createMainButtons(String userId) {
        Map<String, String> data = new HashMap<>();

        Callback searchCallback = new Callback(userId, Action.SEARCH_REQUEST_ACTION);
        data.put(resources.getButtonsText().getSearchRequest(), getButtonData(saveTempWithToken(searchCallback)));

        Callback saleCallback = new Callback(userId, Action.SALE_ACTION);
        data.put(resources.getButtonsText().getPlaceAnAd(), getButtonData(saveTempWithToken(saleCallback)));

        Callback ruleCallback = new Callback(userId, Action.RULES_ACTION);
        data.put(resources.getButtonsText().getRules(), getButtonData(saveTempWithToken(ruleCallback)));

        return createInlineKeyBoard(data, 1);

    }

    public InlineKeyboardButton createUniversalButton(Callback callback) {
        callback.setSubsection(CallbackSubsection.UNEVERSAL);
        InlineKeyboardButton b = new InlineKeyboardButton(resources.getButtonsText().getUniversal());
        b.setCallbackData(getButtonData(saveTempWithToken(callback)));
        return b;
    }
//    CreateConcernButton();
//    CreateAutoBrandButton();
//    CreateModelsButton();
//    CreateEnginesButton();
//    CreateBoltPatternsButton();
//    CreateRegionsButton();
//    getButtonData();

    private String saveTempWithToken(Callback callback) {
        String token = Util.newMd5FromCalBack(callback);
        storage.saveTempData(token, callback);
        return token;
    }

    private String getButtonData(String token) {
        return "{\"token\" : \"" + token + "\"}";
    }

    private InlineKeyboardMarkup createInlineKeyBoard(Map<String, String> data, int columnCount) {
        //int rowCount = Math.round((float) data.size() / columnCount);
        List<List<InlineKeyboardMarkup>> rows = new ArrayList<>();
        InlineKeyboardMarkup.InlineKeyboardMarkupBuilder a = InlineKeyboardMarkup.builder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            InlineKeyboardButton b = new InlineKeyboardButton(entry.getKey());
            b.setCallbackData(entry.getValue());
            buttons.add(b);
            a.keyboardRow(buttons);
        }

        return a.build();
    }
}
