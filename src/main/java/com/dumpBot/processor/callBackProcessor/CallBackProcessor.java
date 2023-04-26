package com.dumpBot.processor.callBackProcessor;

import com.dumpBot.bot.ICallBackProcessor;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.Token;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.BaseProcess;
import com.dumpBot.processor.IStorage;
import com.dumpBot.processor.ResourcesHelper;
import com.dumpBot.processor.callBackProcessor.process.CallBackProcess;
import com.dumpBot.processor.callBackProcessor.process.CallBackProcessFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@NoArgsConstructor
public class CallBackProcessor extends BaseProcess implements ICallBackProcessor {
    @Autowired
    ResourcesHelper resourcesHelper;
    @Autowired
    IStorage storage;
    @Autowired
    ILogger logger;

    @Override
    public SendMessage startCallbackProcessor(Update update) {
        Callback callback = getCallBackByUpdate(update);
        CallBackProcess process = CallBackProcessFactory.getProcess(callback.getAction());
        return process.execute(update, callback);
    }

    private Callback getCallBackByUpdate(Update update) {
        String callBack = update.getCallbackQuery().getData();
        logger.writeInfo("Received the following callback from user update "
                + update.getCallbackQuery().getFrom().getId() + ": " + callBack);

        String token = parseToken(callBack);
        Callback callback = storage.getTempData(token);
        logger.writeInfo("Found callback from temp :" + callback.toString());
        return callback;
    }


    private String parseToken(String callBack) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(callBack, Token.class).getToken();
        } catch (JsonProcessingException e) {
            logger.writeStackTrace(e);
            throw new RuntimeException(e);
        }

    }
}
