package com.dumpBot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface IPhotoProcessor {
    SendMessage startPhotoProcessor(Update update);
}
