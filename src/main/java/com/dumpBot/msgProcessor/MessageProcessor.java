package com.dumpBot.msgProcessor;

import com.dumpBot.bot.IMessageProcessor;
import com.dumpBot.common.Util;
import com.dumpBot.config.Config;
import com.dumpBot.msgProcessor.process.Process;
import com.dumpBot.msgProcessor.process.ProcessFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageProcessor implements IMessageProcessor {
    Config config;

    @Override
    public SendMessage startMessageProcessor(Update update) {
        //TODO проверяем пользователя, если не зарегистрирован - предлагаем регистрацию.
        config = Config.init();
        ProcessFactory processFactory = new ProcessFactory();
        Command command = Util.findEnumConstant(Command.class, update.getMessage().getText());
        Process process = processFactory.getProcess(command);
        return process.execute();
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
