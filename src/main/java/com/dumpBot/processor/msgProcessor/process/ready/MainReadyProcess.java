package com.dumpBot.processor.msgProcessor.process.ready;

import com.dumpBot.common.Util;
import com.dumpBot.config.Config;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.User;
import com.dumpBot.model.enums.Action;
import com.dumpBot.storage.IUserStorage;
import com.dumpBot.processor.msgProcessor.process.BaseMsgProcess;
import com.dumpBot.processor.msgProcessor.process.MsgProcess;
import com.dumpBot.resources.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;

@Component
public class MainReadyProcess extends BaseMsgProcess implements MsgProcess {
    @Autowired
    ILogger logger;
    @Autowired
    SaleReadyProcess saleReadyProcess;
    @Autowired
    SearchReadyProcess searchReadyProcess;
    @Autowired
    IUserStorage userStorage;
    @Autowired
    Resources resources;

    @Autowired
    Config config;

    @Override
    public void processResultPreviousStep() {
        //no use
    }

    @Override
    public void preparationCurrentProcess() {
        //no use
    }

    @Override
    public List<SendMessage> execute(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start ready process for user " + userId);
        User user = userStorage.getUser(userId);
        if (user == null || user.getClientAction() == null) {
            return sendError(userId);
        }
        switch (Util.findEnumConstant(Action.class, user.getClientAction())) {
            case SALE -> {
                return saleReadyProcess.execute(update);
            }
            case SEARCH -> {
                return searchReadyProcess.execute(update);
            }
            default -> {
                return sendError(userId);
            }
        }
    }

    private List<SendMessage> sendError(String userId) {
        return Collections.singletonList(new SendMessage(userId, resources.getErrors().getCommonError()));
    }
}
