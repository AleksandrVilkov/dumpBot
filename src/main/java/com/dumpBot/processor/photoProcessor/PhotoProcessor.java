package com.dumpBot.processor.photoProcessor;

import com.dumpBot.bot.IPhotoProcessor;
import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.LastCallback;
import com.dumpBot.model.User;
import com.dumpBot.processor.BaseProcess;
import com.dumpBot.storage.IUserStorage;
import com.dumpBot.resources.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PhotoProcessor extends BaseProcess implements IPhotoProcessor {
    @Autowired
    IUserStorage storage;
    @Autowired
    ILogger logger;
    @Autowired
    Resources resources;

    @Override
    public List<SendMessage> startPhotoProcessor(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start photo processor for " + userId, this.getClass());
        List<String> photos = Collections.singletonList(String.valueOf(update.getMessage().getPhoto().get(0).getFileId()));
        User user = storage.getUser(userId);
        if (user == null) {
            logger.writeInfo("user is null: userId " + userId, this.getClass());
            return Collections.singletonList(new SendMessage(userId,
                    resources.getMsgs().getPhoto().getNoRegistration()));
        }
        String stringLastCallback = user.getLastCallback();
        logger.writeInfo("found callback from user " + userId + ": " + stringLastCallback, this.getClass());
        if (stringLastCallback == null) {
            logger.writeInfo("lastCallback is null: userId " + userId, this.getClass());
            return Collections.singletonList(new SendMessage(userId,
                    resources.getMsgs().getPhoto().getNoAction()));
        }
        try {
            LastCallback lastCallback = Util.readLastCallback(stringLastCallback);
            logger.writeInfo("callback from user " + userId + " read successfully", this.getClass());
            if (lastCallback.getPhotos() == null) {
                lastCallback.setPhotos(new ArrayList<>());
            }
            lastCallback.getPhotos().addAll(photos);
            user.setLastCallback(lastCallback.toString());
            storage.saveUser(user);
        } catch (Exception e) {
            logger.writeStackTrace(e);
            return Collections.singletonList(new SendMessage(userId,
                    resources.getErrors().getCommonError()));
        }

        return Collections.singletonList(new SendMessage(userId,
                resources.getMsgs().getPhoto().getSuccessSavedToLastCallBack()));

    }

}
