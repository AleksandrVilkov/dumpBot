package com.dumpBot.processor.msgProcessor;

import com.dumpBot.bot.IMessageProcessor;
import com.dumpBot.common.Util;
import com.dumpBot.config.Config;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.Action;
import com.dumpBot.model.User;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.BaseProcess;
import com.dumpBot.processor.IStorage;
import com.dumpBot.processor.msgProcessor.process.Command;
import com.dumpBot.processor.msgProcessor.process.MsgProcess;
import com.dumpBot.processor.msgProcessor.process.MsgProcessFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageProcessor extends BaseProcess implements IMessageProcessor {
    Config config;
    @Autowired
    IStorage storage;
    @Autowired
    ILogger logger;

    public MessageProcessor() {
        super();
        this.config = Config.init();
    }

    @Override
    public SendMessage startMessageProcessor(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start message processor for " + userId);
        MsgProcess process;
        if (!storage.checkUser(userId)) {
            String stringCommand = update.getMessage().getText().toUpperCase().replace("/", "");
            Command command = Util.findEnumConstant(Command.class, stringCommand);
            logger.writeInfo("Сommand " + command.getName() + " recognized by user " + userId);
            process = MsgProcessFactory.getProcess(command);
        } else {
            User user = storage.getUser(userId);
            ObjectMapper objectMapper = new ObjectMapper();
            String stringCallBack = user.getLastCallback();
            Callback callback;
            try {
                callback = objectMapper.readValue(stringCallBack, Callback.class);
                logger.writeInfo("callback " + callback.toString() + " was found by user " + userId);
            } catch (Exception e) {
                logger.writeStackTrace(e);
            }

            if (user.isWaitingMessages()) {
                Action action = Util.findEnumConstant(Action.class, user.getClientAction());
                process = MsgProcessFactory.getProcess(action);
                logger.writeInfo("The user " + userId + " is waiting for a text message on the process " + action.name());

            } else {
                process = MsgProcessFactory.getProcess(Command.MAIN_MENU);
                logger.writeInfo("User " + userId + " does not expect a text message. create the main menu");
            }

        }
        return process.execute(update);
    }

    @Override
    public SendMessage createError() {
        return null;
    }

    @Override
    public SendMessage createErrAuthMsg() {
        return null;
    }
}
