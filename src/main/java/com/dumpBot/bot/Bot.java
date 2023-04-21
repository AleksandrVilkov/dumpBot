package com.dumpBot.bot;

import com.dumpBot.config.Config;
import com.dumpBot.loger.ILogger;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Getter
@Setter
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private ICallBackProcessor callBackProcessor;
    @Autowired
    private IMessageProcessor messageProcessor;
    @Autowired
    private  IPhotoProcessor photoProcessor;
    @Autowired
    private ILogger logger;
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
        logger.writeInfo("new update received");
        if (!Validator.validateUser(update, this, config)) {
            try {
                execute(messageProcessor.createErrAuthMsg(update));
            } catch (TelegramApiException e) {
                logger.writeStackTrace(e);
                throw new RuntimeException(e);
            }
            return;
        }

        try {
            if (update.hasMessage()) {
                if (update.getMessage().getText() != null) {
                    //Смотрим сообщение
                    logger.writeInfo("new update is message from " + update.getMessage().getFrom().getId());
                    execute(messageProcessor.startMessageProcessor(update));
                    return;
                }

                if (update.getMessage().getPhoto().size() > 0) {
                    //смотрим фото
                    logger.writeInfo("new update is photo from " + update.getMessage().getFrom().getId());
                    execute(photoProcessor.startPhotoProcessor(update));
                    return;
                }
            }

            if (update.hasCallbackQuery()) {
                logger.writeInfo("new update is callback");
                execute(callBackProcessor.startCallbackProcessor(update));
            } else {
                execute(messageProcessor.createError(update));
            }

        } catch (TelegramApiException e) {
            logger.writeStackTrace(e);
            throw new RuntimeException(e);
        }
    }
}

