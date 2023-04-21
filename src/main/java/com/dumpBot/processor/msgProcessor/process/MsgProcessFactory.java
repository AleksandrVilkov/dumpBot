package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.model.Action;
import com.dumpBot.processor.msgProcessor.StartRegistrationMsgProcess;
import com.dumpBot.processor.msgProcessor.process.defaultProcess.DefaultProcess;
import com.dumpBot.processor.msgProcessor.process.sale.SaleDescriptionMsgProcess;
import com.dumpBot.processor.msgProcessor.process.sale.SalePriceMsgProcess;
import com.dumpBot.processor.msgProcessor.process.search.SearchMsgProcess;
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
    public MsgProcessFactory(StartRegistrationMsgProcess srp, StartProcess mmp, DefaultProcess df,
                             SearchMsgProcess sp, ReadyMsgProcess rp, SalePriceMsgProcess spp, SaleDescriptionMsgProcess sdp) {
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
