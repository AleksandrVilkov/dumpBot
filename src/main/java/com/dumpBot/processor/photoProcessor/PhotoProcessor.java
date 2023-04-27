package com.dumpBot.processor.photoProcessor;

import com.dumpBot.bot.IPhotoProcessor;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.User;
import com.dumpBot.processor.BaseProcess;
import com.dumpBot.processor.IUserStorage;
import com.dumpBot.processor.ResourcesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.Collections;
import java.util.List;

@Component
public class PhotoProcessor extends BaseProcess implements IPhotoProcessor {
    @Autowired
    IUserStorage storage;
    @Autowired
    ILogger logger;
    @Autowired
    ResourcesHelper resourcesHelper;

    @Override
    public List<SendMessage> startPhotoProcessor(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start photo processor for " + userId);
        List<String> photos = Collections.singletonList(String.valueOf(update.getMessage().getPhoto().get(0).getFileId()));
        User user = storage.getUser(userId);
        if (user == null) {
            return Collections.singletonList(new SendMessage(userId,
                    resourcesHelper.getResources().getMsgs().getPhoto().getNoRegistration()));
        }
        String lastCallback = user.getLastCallback();
        if (lastCallback == null) {
            return  Collections.singletonList(new SendMessage(userId,
                    resourcesHelper.getResources().getMsgs().getPhoto().getNoAction()));
        }
        try {
            storage.saveUser(user);
        } catch (Exception e) {
            logger.writeStackTrace(e);
            return  Collections.singletonList(new SendMessage(userId,
                    resourcesHelper.getResources().getErrors().getCommonError()));
        }

        return  Collections.singletonList(new SendMessage(userId,
                resourcesHelper.getResources().getMsgs().getPhoto().getSuccessSavedToLastCallBack()));

    }
}
