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

}
