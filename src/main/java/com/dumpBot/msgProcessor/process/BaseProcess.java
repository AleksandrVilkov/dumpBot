package com.dumpBot.msgProcessor.process;

import com.dumpBot.buttonMaker.ButtonMaker;
import com.dumpBot.resources.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseProcess {
    @Autowired
    ButtonMaker buttonMaker;
    @Autowired
    MessageTextCreator messageTextCreator;

    Resources resources;

    public BaseProcess() {
            this.resources = Resources.init();
    }
}
