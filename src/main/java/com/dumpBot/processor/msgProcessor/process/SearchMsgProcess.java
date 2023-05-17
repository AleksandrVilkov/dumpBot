package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.loger.ILogger;
import com.dumpBot.model.User;
import com.dumpBot.model.enums.Action;
import com.dumpBot.processor.IUserStorage;
import com.dumpBot.processor.msgProcessor.process.BaseMsgProcess;
import com.dumpBot.processor.msgProcessor.process.MsgProcess;
import com.dumpBot.resources.Resources;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@NoArgsConstructor
public class SearchMsgProcess extends BaseMsgProcess implements MsgProcess {
    @Autowired
    IUserStorage userStorage;
    @Autowired
    ILogger logger;
    @Autowired
    Resources resources;

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
        if (user == null /* || user.getClientAction() != Action.SEARCH.name()*/) {
            return sendError(userId);
        }
        List<SendMessage> result = new ArrayList<>();
        result.add(new SendMessage(userId, resources.getMsgs().getSearch().getTitle()));
        SendMessage ready = new SendMessage(userId, resources.getMsgs().getSearch().getBotReady());
        ready.setReplyMarkup(new ReplyKeyboardRemove(true));
        result.add(ready);
        return result;
    }

    private List<SendMessage> sendError(String userId) {
        return Collections.singletonList(new SendMessage(userId, resources.getErrors().getCommonError()));
    }
}
