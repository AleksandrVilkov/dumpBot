package com.dumpBot.processor.callBackProceccor.process.sale;

import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.*;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.model.callback.CarData;
import com.dumpBot.processor.IStorage;
import com.dumpBot.processor.ResourcesHelper;
import com.dumpBot.processor.callBackProceccor.process.CallBackProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SaleCallBackProcess implements CallBackProcess {
    @Autowired
    IStorage storage;
    @Autowired
    ResourcesHelper resourcesHelper;
    @Autowired
    ILogger logger;

    @Override
    public SendMessage execute(Update update, Callback callback) {
        String userId = String.valueOf(update.getCallbackQuery().getFrom().getId());
        logger.writeInfo("start sale callBack process for user " + userId);
        if (callback.getSubsection() == null) {
            logger.writeInfo(" user " + userId + "chooses сoncern for sale");
            return choiceConcern(update, callback);
        }
        switch (callback.getSubsection()) {
            case CHOOSE_CONCERN -> {
                logger.writeInfo("user " + userId + "chooses brand for sale");
                return choiceBrand(update, callback);
            }
            case CHOOSE_BRAND -> {
                logger.writeInfo("user " + userId + "chooses model for sale");
                return choiceModel(update, callback);
            }
            case CHOOSE_MODEL -> {
                logger.writeInfo("user " + userId + "chooses engine for sale");
                return choiceEngine(update, callback);
            }
            case PRICE, UNIVERSAL -> {
                logger.writeInfo("user " + userId + "enter description for sale");
                return enterPrice(update, callback);
            }
            default -> {
                logger.writeWarning("for user " + userId + " could not define subsection");
                return null;
            }
        }
    }

    private SendMessage enterDescription(Update update, Callback callback) {
        User usr = storage.getUser(String.valueOf(update.getCallbackQuery().getFrom().getId()));
        usr.setWaitingMessages(true);
        callback.setSubsection(CallbackSubsection.DESCRIPTION);
        usr.setClientAction(Action.SALE_ACTION.toString());
        usr.setLastCallback(callback.toString());
        storage.saveUser(usr);
        return new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getSale().getEnterDescription());
    }
    private SendMessage enterPrice(Update update, Callback callback) {
        User usr = storage.getUser(String.valueOf(update.getCallbackQuery().getFrom().getId()));
        usr.setWaitingMessages(true);
        callback.setSubsection(CallbackSubsection.PRICE);
        usr.setClientAction(Action.SALE_PRICE_ACTION.toString());
        usr.setLastCallback(callback.toString());
        storage.saveUser(usr);
        return new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getSale().getPriceEnter());
    }

    private SendMessage choiceModel(Update update, Callback callback) {
        List<Model> models = storage.getModels(callback.getCarData().getBrand());
        Map<String, String> data = new HashMap<>();
        insertUniversalDataButton(data, callback);
        for (Model model : models) {
            callback.setSubsection(CallbackSubsection.CHOOSE_MODEL);
            callback.getCarData().setModel(model.getName());
            String token = Util.newMd5FromCalBack(callback);
            data.put(model.getName(), resourcesHelper.getButtonData(token));
            storage.saveTempData(token, callback);
        }
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getCarModelEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceEngine(Update update, Callback callback) {
        List<Engine> engines = storage.getEngines(callback.getCarData().getBrand(),
                callback.getCarData().getModel());
        Map<String, String> data = new HashMap<>();
        insertUniversalDataButton(data, callback);
        for (Engine engine : engines) {
            callback.setSubsection(CallbackSubsection.PRICE);
            callback.getCarData().setEngineName(engine.getName());
            String token = Util.newMd5FromCalBack(callback);
            data.put(engine.getName(), resourcesHelper.getButtonData(token));
            storage.saveTempData(token, callback);
        }
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getCarEngineEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceBrand(Update update, Callback callback) {
        List<Brand> brands = storage.getBrands(callback.getCarData().getConcern());
        Map<String, String> data = new HashMap<>();
        insertUniversalDataButton(data, callback);
        for (Brand brand : brands) {
            callback.setSubsection(CallbackSubsection.CHOOSE_BRAND);
            callback.getCarData().setBrand(brand.getName());
            String token = Util.newMd5FromCalBack(callback);
            data.put(brand.getName(), resourcesHelper.getButtonData(token));
            storage.saveTempData(token, callback);
        }
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getSale().getCarBrandEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceConcern(Update update, Callback callback) {
        List<Concern> concerns = storage.getConcerns();
        Map<String, String> data = new HashMap<>();
        insertUniversalDataButton(data, callback);
        for (Concern concern : concerns) {
            callback.setSubsection(CallbackSubsection.CHOOSE_CONCERN);
            CarData carData = new CarData();
            carData.setConcern(concern.getName());
            callback.setCarData(carData);
            String token = Util.newMd5FromCalBack(callback);
            data.put(concern.getName(), resourcesHelper.getButtonData(token));
            storage.saveTempData(token, callback);
        }

        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getSale().getChoiceConcern());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private void insertUniversalDataButton(Map<String, String> data, Callback callback) {
        Callback newCb = callback.clone();
        newCb.setSubsection(CallbackSubsection.PRICE);
        String token = Util.newMd5FromCalBack(newCb);
        data.put(resourcesHelper.getResources().getButtonsText().getSaleUniversal(), resourcesHelper.getButtonData(token));
        storage.saveTempData(token, newCb);
    }


}
