package com.dumpBot.processor.callBackProceccor;

import com.dumpBot.bot.ICallBackProcessor;
import com.dumpBot.model.Callback;
import com.dumpBot.processor.ResourcesHelper;
import com.dumpBot.processor.callBackProceccor.process.CallBackProcess;
import com.dumpBot.processor.callBackProceccor.process.CallBackProcessFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallBackProcessor implements ICallBackProcessor {
    @Autowired
    ResourcesHelper resourcesHelper;

    @Override
    public SendMessage startCallbackProcessor(Update update) {
        Callback callback = getCallBackByUpdate(update);
        CallBackProcess process = CallBackProcessFactory.getProcess(callback.getAction());
        return process.execute(update,resourcesHelper);
    }

    private Callback getCallBackByUpdate(Update update) {
        String callBack = update.getCallbackQuery().getData();
        String token = parseToken(callBack);
        return resourcesHelper.getStorage().getTempData(token);
    }

    private String parseToken(String callBack) {
        return "";
    }
}
