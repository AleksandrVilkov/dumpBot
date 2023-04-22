package com.dumpBot.processor.callBackProceccor.process.rules;

import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.BaseProcess;
import com.dumpBot.processor.ResourcesHelper;
import com.dumpBot.processor.callBackProceccor.process.CallBackProcess;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@NoArgsConstructor
public class RulesCallbackProcess extends BaseProcess implements CallBackProcess  {
    @Autowired
    ResourcesHelper resourcesHelper;
    @Override
    public SendMessage execute(Update update, Callback callback) {
        return new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()), resourcesHelper.getResources().getRules());
    }
}
