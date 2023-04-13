package com.dumpBot.bot;

import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;

public class Validator {

    public static boolean validateUser(Update update, Bot bot) {
        long id = 0;
        if (update.hasCallbackQuery()) {
            id = update.getCallbackQuery().getFrom().getId();
        } else {
            id = update.getMessage().getFrom().getId();
        }
        return false;
    }
}
