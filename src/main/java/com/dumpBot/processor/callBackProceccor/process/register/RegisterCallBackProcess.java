package com.dumpBot.processor.callBackProceccor.process.register;

import com.dumpBot.model.*;
import com.dumpBot.processor.ResourcesHelper;
import com.dumpBot.processor.callBackProceccor.process.CallBackProcess;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterCallBackProcess implements CallBackProcess {
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
            case CHOOSE_CITY -> {
                return finishRegistration(update, resourcesHelper, update);
            }
            default -> {

            }
        }
        return null;
    }

    private SendMessage choiceModel(Update update, ResourcesHelper resourcesHelper, Callback callback) {
        List<Model> models = resourcesHelper.getStorage().getModels();
        Map<String, String> data = new HashMap<>();

        for (Model model : models) {
            callback.setSubsection(CallbackSubsection.CHOOSE_MODEL);
            data.put(model.getName(), callback.toString());
        }

        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getFrom().getId()),
                resourcesHelper.getResources().getRegistration().getCarModelEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceEngine(Update update, ResourcesHelper resourcesHelper, Callback callback) {
        List<Engine> engines = resourcesHelper.getStorage().getEngines();
        Map<String, String> data = new HashMap<>();

        for (Engine engine : engines) {
            callback.setSubsection(CallbackSubsection.CHOOSE_ENGINE);
            data.put(engine.getName(), callback.toString());
        }

        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getFrom().getId()),
                resourcesHelper.getResources().getRegistration().getCarEngineEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceBoltPattern(Update update, ResourcesHelper resourcesHelper, Callback callback) {
        List<BoltPattern> boltPatterns = resourcesHelper.getStorage().getBoltPattern();
        Map<String, String> data = new HashMap<>();

        for (BoltPattern boltPattern : boltPatterns) {
            callback.setSubsection(CallbackSubsection.CHOOSE_BOLT_PATTERN);
            data.put(boltPattern.getName(), callback.toString());
        }

        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getFrom().getId()),
                resourcesHelper.getResources().getRegistration().getCarBoltPatternEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceCity(Update update, ResourcesHelper resourcesHelper, Callback callback) {
        List<City> cities = resourcesHelper.getStorage().getCities();
        Map<String, String> data = new HashMap<>();

        for (City city : cities) {
            callback.setSubsection(CallbackSubsection.CHOOSE_CITY);
            data.put(city.getName(), callback.toString());
        }

        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getFrom().getId()),
                resourcesHelper.getResources().getRegistration().getRegionEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage finishRegistration(Update update, ResourcesHelper resourcesHelper, Update update1) {
//TODO  сохраняем в базу
        return null;
    }

    private SendMessage choiceBrand(Update update, ResourcesHelper resourcesHelper, Callback callback) {
        List<Brand> brands = resourcesHelper.getStorage().getBrands();
        Map<String, String> data = new HashMap<>();

        for (Brand brand : brands) {
            callback.setSubsection(CallbackSubsection.CHOOSE_BRAND);
            data.put(brand.getName(), callback.toString());
        }

        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getFrom().getId()),
                resourcesHelper.getResources().getRegistration().getCarBrandEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage choiceConcern(Update update, ResourcesHelper resourcesHelper, Callback callback) {
        List<Concern> concerns = resourcesHelper.getStorage().getConcerns();
        Map<String, String> data = new HashMap<>();

        for (Concern concern : concerns) {
            callback.setSubsection(CallbackSubsection.CHOOSE_CONCERN);
            data.put(concern.getName(), callback.toString());
        }
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getFrom().getId()),
                resourcesHelper.getResources().getRegistration().getChoiceConcern());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }


}
