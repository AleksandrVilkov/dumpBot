package com.dumpBot.processor.photoProcessor;

import com.dumpBot.bot.IPhotoProcessor;
import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.User;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.BaseProcess;
import com.dumpBot.processor.IStorage;
import com.dumpBot.processor.ResourcesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

import java.util.ArrayList;
import java.util.List;

@Component
public class PhotoProcessor extends BaseProcess implements IPhotoProcessor {
    @Autowired
    IStorage storage;
    @Autowired
    ILogger logger;
    @Autowired
    ResourcesHelper resourcesHelper;

    @Override
    public SendMessage startPhotoProcessor(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        logger.writeInfo("start photo processor for " + userId);
        SendMediaGroup smgb = new SendMediaGroup();

        List<String> photos = new ArrayList<>();
        List<InputMedia> medias = new ArrayList<>();
        for (PhotoSize photoSize : update.getMessage().getPhoto()) {
            InputMedia inputMedia = new InputMediaPhoto(photoSize.getFileId());
            medias.add(inputMedia);
            photos.add(photoSize.getFileId());
        }

        User user = storage.getUser(userId);
        if (user == null) {
            return new SendMessage(userId, resourcesHelper.getResources().getMsgs().getPhoto().getNoRegistration());
        }
        String lastCallback = user.getLastCallback();
        if (lastCallback == null) {
            return new SendMessage(userId, resourcesHelper.getResources().getMsgs().getPhoto().getNoAction());
        }
        Callback callback;
        try {
            callback = Util.readCallBack(lastCallback);
            logger.writeInfo("callback " + callback.toString() + " was found by user " + userId);
            callback.setPhotos(photos);
            user.setLastCallback(callback.toString());
            storage.saveUser(user);
        } catch (Exception e) {
            logger.writeStackTrace(e);
            return new SendMessage(userId, resourcesHelper.getResources().getErrors().getCommonError());
        }

        return new SendMessage(userId, resourcesHelper.getResources().getMsgs().getPhoto().getSuccessSavedToLastCallBack());

    }
}
