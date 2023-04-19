package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.loger.ILogger;
import com.dumpBot.model.Action;
import com.dumpBot.model.User;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.model.callback.CarData;
import com.dumpBot.processor.IStorage;
import com.dumpBot.processor.ResourcesHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SearchProcess extends BaseProcess implements MsgProcess {
    @Autowired
    IStorage storage;
    @Autowired
    ILogger logger;

    @Autowired
    ResourcesHelper resourcesHelper;

    @Override
    public SendMessage execute(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        User user = storage.getUser(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        String stringCallBack = user.getLastCallback();
        Callback callback = null;
        try {
            callback = objectMapper.readValue(stringCallBack, Callback.class);
            logger.writeInfo("callback " + callback.toString() + " was found by user " + userId);
        } catch (Exception e) {
            logger.writeStackTrace(e);
        }
        if (callback != null) {
            String textMsg = createText(update.getMessage(), callback, user);
            SendMessage sendMessage = new SendMessage("-1001773791815", textMsg);
            resetLastUserAction(user);
            return sendMessage;
        }
        return null;
    }

    private void resetLastUserAction(User user) {
        user.setWaitingMessages(false);
        user.setClientAction(null);
        user.setLastCallback(null);
        storage.saveUser(user);

    }

    private String createText(Message message, Callback callback, User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Куплю: ");
        stringBuilder.append(message.getText()).append("\n");

        CarData carData = callback.getCarData();

        if (carData != null && carData.getConcern() != null) {
            stringBuilder.append("Концерн: ").append(carData.getConcern()).append("\n");
        }
        if (carData != null && carData.getBrand() != null) {
            stringBuilder.append("Бренд: ").append(carData.getBrand()).append("\n");
        }
        if (carData != null && carData.getModel() != null) {
            stringBuilder.append("Модель: ").append(carData.getModel()).append("\n");
        }
        if (carData != null && carData.getEngineName() != null) {
            stringBuilder.append("Двигатель: ").append(carData.getEngineName()).append("\n");
        }
        if (carData != null && carData.getBoltPatternSize() != null) {
            stringBuilder.append("Разболтовка колес: ").append(carData.getBoltPatternSize()).append("\n");
        }

        stringBuilder.append("Писать: @").append(message.getFrom().getUserName()).append("\n");
        stringBuilder.append("Нахожусь в городе ").append(user.getRegion().getName());
        return stringBuilder.toString();
    }
}
