package com.dumpBot.bot;

import com.dumpBot.config.Config;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Validator {

    public static boolean validateUser(Update update, Bot bot, Config config) {
        long id = 0;
        if (update.hasCallbackQuery()) {
            id = update.getCallbackQuery().getFrom().getId();
        } else {
            id = update.getMessage().getFrom().getId();
        }

        return false;
    }
}
