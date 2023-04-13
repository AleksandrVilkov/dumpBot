package com.dumpBot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {


//    @Autowired
//    ICallBackProcessor callBackProcessor;
//    @Autowired
//    IMessageProcessor messageProcessor;

    public Bot() {

    }

    @Override
    public String getBotUsername() {
        return "psa_dump_dev";
    }

    @Override
    public String getBotToken() {
        return "5990503214:AAGAlWdWPbKQCSvZsRfR9l48tAkuqTcFdws";
    }

    @Override
    public void onUpdateReceived(Update update) {
//        try {
//            if (update.hasMessage() && update.getMessage().hasText()) {
//                execute(messageProcessor.startMessageProcessor());
//                return;
//            }
//            if (update.hasCallbackQuery()) {
//                execute(callBackProcessor.startCallbackProcessor());
//            }
//
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
    }
}

