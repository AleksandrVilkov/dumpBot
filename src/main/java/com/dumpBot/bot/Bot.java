package com.dumpBot.bot;

import com.dumpBot.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {
    @Autowired
    ICallBackProcessor callBackProcessor;
    @Autowired
    IMessageProcessor messageProcessor;
    Config config;

    public Bot() {
       this.config = Config.init();
    }

    @Override
    public String getBotUsername() {
        return config.getBot().getName();
    }

    @Override
    public String getBotToken() {
        return config.getBot().getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!Validator.validateUser(update, this, config)) {
            try {
                execute(messageProcessor.createErrAuthMsg());
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                execute(messageProcessor.startMessageProcessor(update));
                return;
            }
            if (update.hasCallbackQuery()) {
                execute(callBackProcessor.startCallbackProcessor());
            }

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

