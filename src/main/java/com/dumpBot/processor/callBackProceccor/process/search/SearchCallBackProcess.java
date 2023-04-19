package com.dumpBot.processor.callBackProceccor.process.search;

import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.*;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.model.callback.CarData;
import com.dumpBot.model.callback.UserData;
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
public class SearchCallBackProcess implements CallBackProcess {

    @Autowired
    IStorage storage;
    @Autowired
    ResourcesHelper resourcesHelper;
    @Autowired
    ILogger logger;

    @Override
    public SendMessage execute(Update update, Callback callback) {
        String userId = String.valueOf(update.getCallbackQuery().getFrom().getId());
        logger.writeInfo("start search callBack process for user " + userId);
        if (callback.getSubsection() == null) {
            logger.writeInfo(" user " + userId + "chooses сoncern for search request");
            return choiceConcern(update, callback);
        }
        switch (callback.getSubsection()) {
            case CHOOSE_CONCERN -> {
                logger.writeInfo("user " + userId + "chooses brand for search request");
                return choiceBrand(update, callback);
            }
            case CHOOSE_BRAND -> {
                logger.writeInfo("user " + userId + "chooses model for search request");
                return choiceModel(update, callback);
            }
            case CHOOSE_MODEL -> {
                logger.writeInfo("user " + userId + "chooses engine for search request");
                return choiceEngine(update, callback);
            }
            case CHOOSE_ENGINE -> {
                logger.writeInfo("user " + userId + "chooses bolt pattern for search request");
                return choiceBoltPattern(update, callback);
            }
            case CHOOSE_BOLT_PATTERN -> {
                logger.writeInfo("user " + userId + "chooses city for search request");
                return choiceCity(update, callback);
            }
            case UNEVERSAL -> {
                logger.writeInfo("user " + userId + "enter description for search request");
                return enterDescription(update, callback);
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
        usr.setClientAction(Action.SEARCH_REQUEST_ACTION.toString());
        usr.setLastCallback(callback.toString());
        storage.saveUser(usr);
        return new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getSearch().getEnterDescription());
    }

    private SendMessage choiceModel(Update update, Callback callback) {
        List<Model> models = storage.getModels(callback.getCarData().getBrand());
        Map<String, String> data = new HashMap<>();

        for (Model model : models) {
            callback.setSubsection(CallbackSubsection.CHOOSE_MODEL);
            callback.getCarData().setModel(model.getName());
            String token = Util.newMd5FromCalBack(callback);
            data.put(model.getName(), resourcesHelper.getButtonData(token));
            storage.saveTempData(token, callback);
        }
        insertUniversalDataButton(data, callback);
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getCarModelEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceEngine(Update update, Callback callback) {
        List<Engine> engines = storage.getEngines(callback.getCarData().getBrand(),
                callback.getCarData().getModel());
        Map<String, String> data = new HashMap<>();

        for (Engine engine : engines) {
            callback.setSubsection(CallbackSubsection.CHOOSE_ENGINE);
            callback.getCarData().setEngineName(engine.getName());
            String token = Util.newMd5FromCalBack(callback);
            data.put(engine.getName(), resourcesHelper.getButtonData(token));
            storage.saveTempData(token, callback);
        }
        insertUniversalDataButton(data, callback);
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getCarEngineEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceBoltPattern(Update update, Callback callback) {
        List<BoltPattern> boltPatterns = storage.getBoltPattern(callback.getCarData().getBrand(),
                callback.getCarData().getModel());
        Map<String, String> data = new HashMap<>();

        for (BoltPattern boltPattern : boltPatterns) {
            callback.setSubsection(CallbackSubsection.CHOOSE_BOLT_PATTERN);
            callback.getCarData().setBoltPatternSize(boltPattern.getName());
            String token = Util.newMd5FromCalBack(callback);
            data.put(boltPattern.getName(), resourcesHelper.getButtonData(token));
            storage.saveTempData(token, callback);
        }
        insertUniversalDataButton(data, callback);
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getCarBoltPatternEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceCity(Update update, Callback callback) {
        List<City> cities = storage.getCities();
        Map<String, String> data = new HashMap<>();

        for (City city : cities) {
            callback.setSubsection(CallbackSubsection.CHOOSE_CITY);
            if (callback.getUserData() == null) {
                callback.setUserData(new UserData());
            }
            callback.getUserData().setRegionId(city.getRegionId());
            callback.getUserData().setRegionName(city.getName());
            String token = Util.newMd5FromCalBack(callback);
            data.put(city.getName(), resourcesHelper.getButtonData(token));
            storage.saveTempData(token, callback);
        }
        insertUniversalDataButton(data, callback);
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getRegionEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceBrand(Update update, Callback callback) {
        List<Brand> brands = storage.getBrands(callback.getCarData().getConcern());
        Map<String, String> data = new HashMap<>();

        for (Brand brand : brands) {
            callback.setSubsection(CallbackSubsection.CHOOSE_BRAND);
            callback.getCarData().setBrand(brand.getName());
            String token = Util.newMd5FromCalBack(callback);
            data.put(brand.getName(), resourcesHelper.getButtonData(token));
            storage.saveTempData(token, callback);
        }

        insertUniversalDataButton(data, callback);
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getSearch().getCarBrandEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceConcern(Update update, Callback callback) {
        List<Concern> concerns = storage.getConcerns();
        Map<String, String> data = new HashMap<>();
        for (Concern concern : concerns) {
            callback.setSubsection(CallbackSubsection.CHOOSE_CONCERN);
            CarData carData = new CarData();
            carData.setConcern(concern.getName());
            callback.setCarData(carData);
            String token = Util.newMd5FromCalBack(callback);
            data.put(concern.getName(), resourcesHelper.getButtonData(token));
            storage.saveTempData(token, callback);
        }
        insertUniversalDataButton(data, callback);
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getChoiceConcern());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private void insertUniversalDataButton(Map<String, String> data, Callback callback) {
        Callback newCb = callback.clone();
        newCb.setSubsection(CallbackSubsection.UNEVERSAL);
        String token = Util.newMd5FromCalBack(callback);
        data.put(resourcesHelper.getResources().getButtonsText().getUniversal(), resourcesHelper.getButtonData(token));
        storage.saveTempData(token, newCb);
    }

}
