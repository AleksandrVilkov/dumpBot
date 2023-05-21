package com.dumpBot.processor.msgProcessor;

import com.dumpBot.bot.IMessageProcessor;
import com.dumpBot.common.Util;
import com.dumpBot.config.Config;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.enums.Action;
import com.dumpBot.model.User;
import com.dumpBot.processor.BaseProcess;
import com.dumpBot.storage.IUserStorage;
import com.dumpBot.processor.msgProcessor.process.Command;
import com.dumpBot.resources.Resources;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@NoArgsConstructor
public class MessageProcessor extends BaseProcess implements IMessageProcessor {
   @Autowired
    Config config;
    @Autowired
    IUserStorage storage;
    @Autowired
    ILogger logger;
    @Autowired
    Resources resources;

    @Override
    public List<SendMessage> startMessageProcessor(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start processor", this.getClass());
        List<MessageEntity> messageEntityList = update.getMessage().getEntities();
        MsgProcess process;
        //Если это команда - получаем MessageEntity bot_command,если команды нет - то лист пустой
        boolean isCommand = messageEntityList != null && messageEntityList.size() > 0;
        boolean userCreated = storage.checkUser(userId);
        if (!userCreated) {
            process = MsgProcessFactory.getProcess(Action.REGISTRATION);
            process.processResultPreviousStep();
            process.preparationCurrentProcess();
            return process.execute(update);
        }
        process = isCommand ? handleCommand(update) : handleMessageText(update);
        process.processResultPreviousStep();
        process.preparationCurrentProcess();
        return process.execute(update);
    }

    private MsgProcess handleMessageText(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        User user = storage.getUser(userId);
        if (user.isWaitingMessages()) {
            Action action = Util.findEnumConstant(Action.class, user.getClientAction());
            return MsgProcessFactory.getProcess(action);
        }
        return MsgProcessFactory.getProcess(Command.START);
    }

    private MsgProcess handleCommand(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        String stringCommand = update.getMessage().getText().toUpperCase().replace("/", "");
        Command command = Util.findEnumConstant(Command.class, stringCommand);
        logger.writeInfo("Сommand " + command.getName() + " recognized by user " + userId, this.getClass());
        return MsgProcessFactory.getProcess(command);
    }

    @Override
    public SendMessage createErrAuthMsg(Update update) {
        String chatId = String.valueOf(update.getMessage() != null ? update.getMessage().getFrom().getId() :
                update.getCallbackQuery().getFrom().getId());
        return new SendMessage(chatId, resources.getErrors().getAuthError());
    }
}
