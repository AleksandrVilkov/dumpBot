package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.resources.Resources;
import org.springframework.stereotype.Component;

@Component
public class BaseProcess {
    Resources resources;
    public BaseProcess() {
            this.resources = Resources.init();
    }
}
