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
import com.dumpBot.processor.ResourcesHelper;
import com.dumpBot.processor.msgProcessor.process.Command;
import com.dumpBot.processor.msgProcessor.process.MsgProcess;
import com.dumpBot.processor.msgProcessor.process.MsgProcessFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class MessageProcessor extends BaseProcess implements IMessageProcessor {
    Config config;
    @Autowired
    IStorage storage;
    @Autowired
    ILogger logger;
    @Autowired
    ResourcesHelper resourcesHelper;

    public MessageProcessor() {
        super();
        this.config = Config.init();
    }

    @Override
    public SendMessage startMessageProcessor(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start message processor for " + userId);
        List<MessageEntity> messageEntityList = update.getMessage().getEntities();
        MsgProcess process;
        //Если это команда - получаем MessageEntity bot_command,если команды нет - то лист пустой
        boolean isCommand = messageEntityList != null && messageEntityList.size() > 0;
        boolean userCreated = storage.checkUser(userId);
        if (!userCreated) {
            process = MsgProcessFactory.getProcess(Command.REGISTRATION);
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
        //находим пользователя и смотрим его последний колбек
        User user = storage.getUser(userId);
        String stringCallBack = user.getLastCallback();
        Callback callback;
        try {
            callback = Util.readCallBack(stringCallBack);
            logger.writeInfo("callback " + callback.toString() + " was found by user " + userId);
        } catch (Exception e) {
            logger.writeStackTrace(e);
        }
        //Если мы ждем от него какой то текст - то смотрим что у него за действие и запускаем соответвующий процесс
        if (user.isWaitingMessages()) {
            Action action = Util.findEnumConstant(Action.class, user.getClientAction());
            logger.writeInfo("The user " + userId + " is waiting for a text message on the process " + action.name());
            return MsgProcessFactory.getProcess(action);
        }
        logger.writeInfo("User " + userId + " does not expect a text message. create the main menu");
        return MsgProcessFactory.getProcess(Command.START); //Если все мимо - то показываем меню
    }

    private MsgProcess handleCommand(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        String stringCommand = update.getMessage().getText().toUpperCase().replace("/", "");
        Command command = Util.findEnumConstant(Command.class, stringCommand);
        logger.writeInfo("Сommand " + command.getName() + " recognized by user " + userId);
        return MsgProcessFactory.getProcess(command);

    }

    @Override
    public SendMessage createError(Update update) {
        String chatId = String.valueOf(update.getMessage() != null ? update.getMessage().getFrom().getId() :
                update.getCallbackQuery().getFrom().getId());
        return new SendMessage(chatId, resourcesHelper.getResources().getErrors().getCommonError());
    }

    @Override
    public SendMessage createErrAuthMsg(Update update) {
        String chatId = String.valueOf(update.getMessage() != null ? update.getMessage().getFrom().getId() :
                update.getCallbackQuery().getFrom().getId());
        return new SendMessage(chatId, resourcesHelper.getResources().getErrors().getAuthError());
    }
}
