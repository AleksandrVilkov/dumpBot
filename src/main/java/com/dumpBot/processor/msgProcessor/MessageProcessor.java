package com.dumpBot.processor.msgProcessor;

import com.dumpBot.bot.IMessageProcessor;
import com.dumpBot.common.Util;
import com.dumpBot.config.Config;
import com.dumpBot.processor.ResourcesHelper;
import com.dumpBot.processor.msgProcessor.process.Command;
import com.dumpBot.processor.msgProcessor.process.MsgProcess;
import com.dumpBot.processor.msgProcessor.process.MsgProcessFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageProcessor implements IMessageProcessor {
    Config config;
    @Autowired
    ResourcesHelper resourcesHelper;

    public MessageProcessor() {
        this.config = Config.init();
    }

    @Override
    public SendMessage startMessageProcessor(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());

        MsgProcess process;
        if (!resourcesHelper.getStorage().CheckUser(userId)) {
            Command command = Util.findEnumConstant(Command.class, update.getMessage().getText().toUpperCase().replace("/", ""));
            process = MsgProcessFactory.getProcess(command);
        } else {
            process = MsgProcessFactory.getProcess(Command.MAIN_MENU);
        }

        return process.execute(update, this.resourcesHelper);
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
