package com.dumpBot.processor.webAppProcessor.process;

import com.dumpBot.model.WebAppData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class SaleWebAppProcess  implements WebAppProcess {
    @Override
    public boolean processData(Update update, WebAppData webAppData) {
        /*
        * {"concern":"PSA","brand":"Citroen","model":"C3","engine":"EP6C","price":"500","description":"описание","action":"SALE"}
        * */
        return false;
    }

    @Override
    public List<SendMessage> prepareAnswer(Update update) {
        return null;
    }
}
