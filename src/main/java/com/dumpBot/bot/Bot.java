package com.dumpBot.bot;

import com.dumpBot.config.Config;
import com.dumpBot.loger.ILogger;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private IMessageProcessor messageProcessor;
    @Autowired
    private IPhotoProcessor photoProcessor;
    @Autowired
    private IWebAppProcessor webAppProcessor;
    @Autowired
    private IButtonCallbackProcessor buttonCallbackProcessor;
    @Autowired
    private ILogger logger;
    @Autowired
    Config config;

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

        if (update.getMessage().hasSticker()) {
            sendEasterEgg(update);
            return;
        }

        logger.writeInfo("new update received", this.getClass());
        if (!Validator.validateUser(update, this, config)) {
            try {
                logger.writeWarning("user validate error", this.getClass());
                execute(messageProcessor.createErrAuthMsg(update));
            } catch (TelegramApiException e) {
                logger.writeStackTrace(e);
                throw new RuntimeException(e);
            }
            return;
        }

        List<SendMessage> msgs = new ArrayList<>();
        try {

            if (update.hasMessage()) {
                handleMsg(update, msgs);
            }

            if (update.hasCallbackQuery()) {
                handleCallback(update, msgs);
            }
        } catch (TelegramApiException e) {
            logger.writeStackTrace(e);
            throw new RuntimeException(e);
        }

        for (SendMessage sendMessage : msgs) {
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleCallback(Update update, List<SendMessage> msgs) throws TelegramApiException {
        logger.writeInfo("new update with callback " + update.getCallbackQuery().getFrom().getId(), this.getClass());
        logger.writeInfo("callback: " + update.getCallbackQuery().getData(), this.getClass());
        msgs.addAll(buttonCallbackProcessor.startButtonCallbackProcessor(update));
    }

    private void handleMsg(Update update, List<SendMessage> msgs) throws TelegramApiException {
        if (update.getMessage().getText() != null) {
            //Смотрим сообщение
            logger.writeInfo("new update is message from " + update.getMessage().getFrom().getId(), this.getClass());
            msgs.addAll(messageProcessor.startMessageProcessor(update));
        }
        //смотрим фото
        if (update.getMessage().getPhoto() != null && update.getMessage().getPhoto().size() > 0) {
            logger.writeInfo("new update is photo from " + update.getMessage().getFrom().getId(), this.getClass());
            msgs.addAll(photoProcessor.startPhotoProcessor(update));
        }

        //смотрим WebAppData
        if (update.getMessage().getWebAppData() != null) {
            logger.writeInfo("new update is webApp from " + update.getMessage().getFrom().getId() +
                    ": " + update.getMessage().getWebAppData().getData(), this.getClass());
            msgs.addAll(webAppProcessor.startWebAppProcessor(update));
        }
    }
    public void sendEasterEgg(Update update) {
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getFrom().getId()),
                "В жопу себе стикеры засунь, я их не понимаю");
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(update.getMessage().getFrom().getId());
        sendSticker.setSticker(new InputFile("CAACAgIAAxkBAAIVxWR5059UZllJ50QkAlqR75l7KN8xAAL8LAACUsVwS4uxDz6ynF-5LwQ"));
        try {
            execute(sendMessage);
            execute(sendSticker);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

