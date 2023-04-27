package com.dumpBot.processor.webAppProcessor;

import com.dumpBot.bot.IWebAppProcessor;
import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.WebAppData;
import com.dumpBot.model.enums.Action;
import com.dumpBot.processor.BaseProcess;
import com.dumpBot.processor.ResourcesHelper;
import com.dumpBot.processor.webAppProcessor.process.WebAppProcess;
import com.dumpBot.processor.webAppProcessor.process.WebAppProcessFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;

@Component
public class WebAppProcessor extends BaseProcess implements IWebAppProcessor {

    @Autowired
    ILogger logger;

    @Autowired
    ResourcesHelper resourcesHelper;

    @Override
    public List<SendMessage> startWebAppProcessor(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        String data = update.getMessage().getWebAppData().getData();
        WebAppData wpd;
        try {
            wpd = readWebApp(data);
        } catch (Exception e) {
            logger.writeStackTrace(e);
            return Collections.singletonList(new SendMessage(userId, e.getMessage()));
        }

        WebAppProcess wp = WebAppProcessFactory.getProcess(Util.findEnumConstant(Action.class, wpd.getAction()));
        if (wp != null && wp.processData(update, wpd)) {
            return wp.prepareAnswer(update);
        }
        return Collections.singletonList(new SendMessage(userId, resourcesHelper.getResources().getErrors().getCommonError()));
    }


    private static WebAppData readWebApp(String webAppStr) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(webAppStr, WebAppData.class);
    }
}

