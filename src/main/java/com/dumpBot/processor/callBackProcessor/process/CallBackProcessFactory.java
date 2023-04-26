package com.dumpBot.processor.callBackProcessor.process;

import com.dumpBot.model.enums.Action;
import com.dumpBot.processor.callBackProcessor.process.defaultProcess.DefaultCallBackProcess;
import com.dumpBot.processor.callBackProcessor.process.register.RegisterCallBackProcess;
import com.dumpBot.processor.callBackProcessor.process.rules.RulesCallbackProcess;
import com.dumpBot.processor.callBackProcessor.process.sale.SaleCallBackProcess;
import com.dumpBot.processor.callBackProcessor.process.search.SearchCallBackProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CallBackProcessFactory {

    static CallBackProcess registerCallBackProcess;
    static CallBackProcess saleCallBackProcess;
    static CallBackProcess searchCallBackProcess;
    static CallBackProcess defaultCallBackProcess;
    static CallBackProcess rulesCallBackProcess;

    @Autowired
    public CallBackProcessFactory(RegisterCallBackProcess rcp,
                                  SaleCallBackProcess sacp,
                                  SearchCallBackProcess scp,
                                  DefaultCallBackProcess dcp,
                                  RulesCallbackProcess rulescp) {
        CallBackProcessFactory.registerCallBackProcess = rcp;
        CallBackProcessFactory.saleCallBackProcess = sacp;
        CallBackProcessFactory.searchCallBackProcess = scp;
        CallBackProcessFactory.defaultCallBackProcess = dcp;
        CallBackProcessFactory.rulesCallBackProcess = rulescp;
    }

    public static CallBackProcess getProcess(Action action) {
        switch (action) {
            case REGISTRATION_ACTION -> {
                return registerCallBackProcess;
            }
            case SALE_ACTION -> {
                return saleCallBackProcess;
            }
            case SEARCH_REQUEST_ACTION -> {
                return searchCallBackProcess;
            }
            case RULES_ACTION -> {
                return rulesCallBackProcess;
            }
            default -> {
                return defaultCallBackProcess;
            }
        }
    }
}
