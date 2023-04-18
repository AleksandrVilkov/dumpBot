package com.dumpBot.processor.callBackProceccor.process.search;

import com.dumpBot.model.*;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.model.callback.CarData;
import com.dumpBot.model.callback.UserData;
import com.dumpBot.processor.ResourcesHelper;
import com.dumpBot.processor.callBackProceccor.process.CallBackProcess;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchCallBackProcess implements CallBackProcess {

    //TODO сделать универсальную кнопку, расширить энамы, изменить текстовку сообщений под поиск, добавить нужные getSubsection
    @Override
    public SendMessage execute(Update update, ResourcesHelper resourcesHelper, Callback callback) {

        if (callback.getSubsection() == null) {
            return choiceConcern(update, resourcesHelper, callback);
        }
        switch (callback.getSubsection()) {
            case CHOOSE_CONCERN -> {
                return choiceBrand(update, resourcesHelper, callback);
            }
            case CHOOSE_BRAND -> {
                return choiceModel(update, resourcesHelper, callback);
            }
            case CHOOSE_MODEL -> {
                return choiceEngine(update, resourcesHelper, callback);
            }
            case CHOOSE_ENGINE -> {
                return choiceBoltPattern(update, resourcesHelper, callback);
            }
            case CHOOSE_BOLT_PATTERN -> {
                return choiceCity(update, resourcesHelper, callback);
            }
            case UNEVERSAL -> {
                return enterDescription(update, resourcesHelper, callback);
            }
            default -> {
                //TODO отдавать ошибку
                return null;
            }
        }
    }

    private SendMessage enterDescription(Update update, ResourcesHelper resourcesHelper, Callback callback) {
        User usr = resourcesHelper.getStorage().getUser(String.valueOf(update.getCallbackQuery().getFrom().getId()));
        usr.setWaitingMessages(true);
        usr.setClientAction(Action.SEARCH_REQUEST_ACTION.toString());
        usr.setLastCallback(callback.toString());
        resourcesHelper.getStorage().saveUser(usr);
        return new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getSearch().getEnterDescription());
    }

    private SendMessage choiceModel(Update update, ResourcesHelper resourcesHelper, Callback callback) {
        List<Model> models = resourcesHelper.getStorage().getModels(callback.getCarData().getBrand());
        Map<String, String> data = new HashMap<>();

        for (Model model : models) {
            callback.setSubsection(CallbackSubsection.CHOOSE_MODEL);
            callback.getCarData().setModel(model.getName());
            String token = resourcesHelper.saveTempWithToken(callback);
            data.put(model.getName(), resourcesHelper.getButtonData(token));
            resourcesHelper.getStorage().saveTempData(token, callback);
        }
        insertUniversalDataButton(data, callback, resourcesHelper);
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getCarModelEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceEngine(Update update, ResourcesHelper resourcesHelper, Callback callback) {
        List<Engine> engines = resourcesHelper.getStorage().getEngines(callback.getCarData().getBrand(),
                callback.getCarData().getModel());
        Map<String, String> data = new HashMap<>();

        for (Engine engine : engines) {
            callback.setSubsection(CallbackSubsection.CHOOSE_ENGINE);
            callback.getCarData().setEngineName(engine.getName());
            String token = resourcesHelper.saveTempWithToken(callback);
            data.put(engine.getName(), resourcesHelper.getButtonData(token));
            resourcesHelper.getStorage().saveTempData(token, callback);
        }
        insertUniversalDataButton(data, callback, resourcesHelper);
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getCarEngineEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceBoltPattern(Update update, ResourcesHelper resourcesHelper, Callback callback) {
        List<BoltPattern> boltPatterns = resourcesHelper.getStorage().getBoltPattern(callback.getCarData().getBrand(),
                callback.getCarData().getModel());
        Map<String, String> data = new HashMap<>();

        for (BoltPattern boltPattern : boltPatterns) {
            callback.setSubsection(CallbackSubsection.CHOOSE_BOLT_PATTERN);
            callback.getCarData().setBoltPatternSize(boltPattern.getName());
            String token = resourcesHelper.saveTempWithToken(callback);
            data.put(boltPattern.getName(), resourcesHelper.getButtonData(token));
            resourcesHelper.getStorage().saveTempData(token, callback);
        }
        insertUniversalDataButton(data, callback, resourcesHelper);
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getCarBoltPatternEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceCity(Update update, ResourcesHelper resourcesHelper, Callback callback) {
        List<City> cities = resourcesHelper.getStorage().getCities();
        Map<String, String> data = new HashMap<>();

        for (City city : cities) {
            callback.setSubsection(CallbackSubsection.CHOOSE_CITY);
            if (callback.getUserData() == null) {
                callback.setUserData(new UserData());
            }
            callback.getUserData().setRegionId(city.getRegionId());
            callback.getUserData().setRegionName(city.getName());
            String token = resourcesHelper.saveTempWithToken(callback);
            data.put(city.getName(), resourcesHelper.getButtonData(token));
            resourcesHelper.getStorage().saveTempData(token, callback);
        }
        insertUniversalDataButton(data, callback, resourcesHelper);
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getRegionEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceBrand(Update update, ResourcesHelper resourcesHelper, Callback callback) {
        List<Brand> brands = resourcesHelper.getStorage().getBrands(callback.getCarData().getConcern());
        Map<String, String> data = new HashMap<>();

        for (Brand brand : brands) {
            callback.setSubsection(CallbackSubsection.CHOOSE_BRAND);
            callback.getCarData().setBrand(brand.getName());
            String token = resourcesHelper.saveTempWithToken(callback);
            data.put(brand.getName(), resourcesHelper.getButtonData(token));
            resourcesHelper.getStorage().saveTempData(token, callback);
        }

        insertUniversalDataButton(data, callback, resourcesHelper);
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getSearch().getCarBrandEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceConcern(Update update, ResourcesHelper resourcesHelper, Callback callback) {
        List<Concern> concerns = resourcesHelper.getStorage().getConcerns();
        Map<String, String> data = new HashMap<>();
        for (Concern concern : concerns) {
            callback.setSubsection(CallbackSubsection.CHOOSE_CONCERN);
            CarData carData = new CarData();
            carData.setConcern(concern.getName());
            callback.setCarData(carData);
            String token = resourcesHelper.saveTempWithToken(callback);
            data.put(concern.getName(), resourcesHelper.getButtonData(token));
            resourcesHelper.getStorage().saveTempData(token, callback);
        }
        insertUniversalDataButton(data, callback, resourcesHelper);
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getChoiceConcern());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private void insertUniversalDataButton(Map<String, String> data, Callback callback, ResourcesHelper resourcesHelper) {
        Callback newCb = callback.clone();
        newCb.setSubsection(CallbackSubsection.UNEVERSAL);
        String token = resourcesHelper.saveTempWithToken(newCb);
        data.put(resourcesHelper.getResources().getButtonsText().getUniversal(), resourcesHelper.getButtonData(token));
        resourcesHelper.getStorage().saveTempData(token, newCb);
    }

}
