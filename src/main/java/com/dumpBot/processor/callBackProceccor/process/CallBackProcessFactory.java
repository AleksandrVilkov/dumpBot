package com.dumpBot.processor.callBackProceccor.process;

import com.dumpBot.model.Action;
import com.dumpBot.processor.callBackProceccor.process.defaultProcess.DefaultCallBackProcess;
import com.dumpBot.processor.callBackProceccor.process.register.RegisterCallBackProcess;
import com.dumpBot.processor.callBackProceccor.process.sale.SaleCallBackProcess;
import com.dumpBot.processor.callBackProceccor.process.search.SearchCallBackProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CallBackProcessFactory {

    static CallBackProcess registerCallBackProcess;
    static CallBackProcess saleCallBackProcess;
    static CallBackProcess searchCallBackProcess;
    static CallBackProcess defaultCallBackProcess;

    @Autowired
    public CallBackProcessFactory(RegisterCallBackProcess rcp,
                                  SaleCallBackProcess sacp,
                                  SearchCallBackProcess scp,
                                  DefaultCallBackProcess dcp) {
        CallBackProcessFactory.registerCallBackProcess = rcp;
        CallBackProcessFactory.saleCallBackProcess = sacp;
        CallBackProcessFactory.searchCallBackProcess = scp;
        CallBackProcessFactory.defaultCallBackProcess = dcp;
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
            default -> {
                return defaultCallBackProcess;
            }
        }
    }
}
