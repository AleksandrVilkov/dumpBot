package com.dumpBot.buttonMaker;

import com.dumpBot.common.Util;
import com.dumpBot.model.Action;
import com.dumpBot.model.Callback;
import com.dumpBot.resources.Resources;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ButtonMaker {
    Resources resources;
    IStorage storage;


    public ButtonMaker() {
        this.resources = Resources.init();
    }

    public InlineKeyboardMarkup CreateRegistrationButton(Update update) {
        Map<String, String> data = new HashMap<>();
        Callback callback = new Callback();
        callback.setAction(Action.REGISTRATION_ACTION);
        callback.setUserId(update.getMessage().getChatId().toString());
        String token = saveTempWithToken(callback);
        data.put(resources.getButtonsText().getRegistration(), getButtonData(token));
        return createInlineKeyBoard(data, 1);

    }
//    CreateMainButtons();
//    CreateUniversalButton();
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
