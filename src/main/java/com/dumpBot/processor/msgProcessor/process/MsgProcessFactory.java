package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.model.Action;
import com.dumpBot.processor.msgProcessor.StartRegistrationProcess;
import com.dumpBot.processor.msgProcessor.process.defaultProcess.DefaultProcess;
import com.dumpBot.processor.msgProcessor.process.sale.SaleDescriptionProcess;
import com.dumpBot.processor.msgProcessor.process.sale.SalePriceProcess;
import com.dumpBot.processor.msgProcessor.process.search.SearchProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsgProcessFactory {
    private static MsgProcess registration;
    private static MsgProcess start;
    private static MsgProcess salePrice;
    private static MsgProcess defaultProcess;
    private static MsgProcess searchProcess;
    private static MsgProcess readyProcess;
    private static MsgProcess saleDescription;

    @Autowired
    public MsgProcessFactory(StartRegistrationProcess srp, StartProcess mmp, DefaultProcess df,
                             SearchProcess sp, ReadyProcess rp, SalePriceProcess spp, SaleDescriptionProcess sdp) {
        MsgProcessFactory.registration = srp;
        MsgProcessFactory.start = mmp;
        MsgProcessFactory.defaultProcess = df;
        MsgProcessFactory.searchProcess = sp;
        MsgProcessFactory.readyProcess = rp;
        MsgProcessFactory.salePrice = spp;
        MsgProcessFactory.saleDescription = sdp;
    }

    public static MsgProcess getProcess(Command command) {
        switch (command) {
            case START -> {
                return start;
            }
            case READY -> {
                return readyProcess;
            }
            case REGISTRATION -> {
                return registration;
            }
            default -> {
                return defaultProcess;
            }
        }
    }

    public static MsgProcess getProcess(Action action) {
        switch (action) {
            case SEARCH_REQUEST_ACTION -> {
                return searchProcess;
            }
            case SALE_PRICE_ACTION -> {
                return salePrice;
            }
            case SALE_DESCRIPTION -> {
                return saleDescription;
            }
            default -> {
                return defaultProcess;
            }
        }
    }
}
