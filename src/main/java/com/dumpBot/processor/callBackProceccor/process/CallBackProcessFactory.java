package com.dumpBot.processor.callBackProceccor.process;

import com.dumpBot.model.Action;
import com.dumpBot.processor.callBackProceccor.process.defaultProcess.DefaultCallBackProcess;
import com.dumpBot.processor.callBackProceccor.process.register.RegisterCallBackProcess;
import com.dumpBot.processor.callBackProceccor.process.sale.SaleCallBackProcess;
import com.dumpBot.processor.callBackProceccor.process.search.SearchCallBackProcess;

public class CallBackProcessFactory {
    public static CallBackProcess getProcess(Action action) {
        switch (action) {
            case REGISTRATION_ACTION -> {
                return new RegisterCallBackProcess();
            }
            case SALE_ACTION -> {
                return new SaleCallBackProcess();
            }
            case SEARCH_REQUEST_ACTION -> {
                return new SearchCallBackProcess();
            }
            default -> {
                return new DefaultCallBackProcess();
            }
        }
    }
}
