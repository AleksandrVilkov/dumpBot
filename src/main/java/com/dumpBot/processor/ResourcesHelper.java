package com.dumpBot.processor;

import com.dumpBot.resources.Resources;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
@Setter
public class ResourcesHelper {
    private Resources resources;

    public ResourcesHelper() {
        this.resources = Resources.init();
    }

    public InlineKeyboardMarkup createRegistrationButton(String userId, String token) {
        Map<String, String> data = new HashMap<>();
        data.put(resources.getButtonsText().getRegistration(), getButtonData(token));
        return createInlineKeyBoard(data, 1);

    }

    public InlineKeyboardMarkup createInlineKeyBoard(Map<String, String> data, int columnCount) {
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

    public String getButtonData(String token) {
        return "{\"token\" : \"" + token + "\"}";
    }
}
