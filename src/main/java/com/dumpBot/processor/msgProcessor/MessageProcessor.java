package com.dumpBot.processor.msgProcessor;

import com.dumpBot.bot.IMessageProcessor;
import com.dumpBot.common.Util;
import com.dumpBot.config.Config;
import com.dumpBot.model.Action;
import com.dumpBot.model.User;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.BaseProcess;
import com.dumpBot.processor.ResourcesHelper;
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
    ResourcesHelper resourcesHelper;

    public MessageProcessor() {
        super();
        this.config = Config.init();
    }

    @Override
    public SendMessage startMessageProcessor(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());

        MsgProcess process;
        if (!resourcesHelper.getStorage().checkUser(userId)) {
            Command command = Util.findEnumConstant(Command.class, update.getMessage().getText().toUpperCase().replace("/", ""));
            process = MsgProcessFactory.getProcess(command);
        } else {
            User user = resourcesHelper.getStorage().getUser(userId);
            ObjectMapper objectMapper = new ObjectMapper();
            String stringCallBack = user.getLastCallback();
            Callback callback;
            try {
                callback = objectMapper.readValue(stringCallBack, Callback.class);
            } catch (Exception e) {
                //TODO
            }

            if (user.isWaitingMessages()) {
                Action action = Util.findEnumConstant(Action.class, user.getClientAction());

                process = MsgProcessFactory.getProcess(action);
            } else {
                process = MsgProcessFactory.getProcess(Command.MAIN_MENU);
            }

        }
        return process.execute(update, resourcesHelper);
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
