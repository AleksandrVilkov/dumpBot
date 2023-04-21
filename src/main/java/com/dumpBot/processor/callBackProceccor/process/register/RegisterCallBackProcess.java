package com.dumpBot.processor.callBackProceccor.process.register;

import com.dumpBot.common.Util;
import com.dumpBot.model.*;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.model.callback.CarData;
import com.dumpBot.model.callback.UserData;
import com.dumpBot.processor.BaseProcess;
import com.dumpBot.processor.IStorage;
import com.dumpBot.processor.ResourcesHelper;
import com.dumpBot.processor.callBackProceccor.process.CallBackProcess;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@NoArgsConstructor
public class RegisterCallBackProcess extends BaseProcess implements CallBackProcess {

    @Autowired
    ResourcesHelper resourcesHelper;

    @Autowired
    IStorage storage;

    @Override
    public SendMessage execute(Update update, Callback callback) {

        if (callback.getSubsection() == null) {
            return choiceConcern(update, callback);
        }
        switch (callback.getSubsection()) {
            case CHOOSE_CONCERN -> {
                return choiceBrand(update, callback);
            }
            case CHOOSE_BRAND -> {
                return choiceModel(update, callback);
            }
            case CHOOSE_MODEL -> {
                return choiceEngine(update, callback);
            }
            case CHOOSE_ENGINE -> {
                return choiceBoltPattern(update, callback);
            }
            case CHOOSE_BOLT_PATTERN -> {
                return choiceCity(update, callback);
            }
            case CHOOSE_CITY -> {
                return finishRegistration(update, callback);
            }
            default -> {
                //TODO отдавать ошибку
                return null;
            }
        }
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

        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getRegionEnter());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }

    private SendMessage finishRegistration(Update update, Callback callback) {
        City region = new City(callback.getUserData().getRegionId(), callback.getUserData().getRegionName());
        User user = new User(new Date(),
                Role.USER_ROLE,
                String.valueOf(update.getCallbackQuery().getFrom().getId()),
                region,
                convertCarData(callback.getCarData()));
        user.setWaitingMessages(false);
        user.setUserName(update.getCallbackQuery().getFrom().getUserName());
        boolean result = storage.saveUser(user);
        if (result) {
            return new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                    resourcesHelper.getResources().getMsgs().getRegistration().getSuccessRegistration()
            );
        } else {
            return new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                    resourcesHelper.getResources().getMsgs().getRegistration().getErrorRegistration()
            );
        }
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

        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getCarBrandEnter());
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
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getFrom().getId()),
                resourcesHelper.getResources().getMsgs().getRegistration().getChoiceConcern());
        sendMessage.setReplyMarkup(resourcesHelper.createInlineKeyBoard(data, 1));
        return sendMessage;
    }


    private Car convertCarData(CarData carData) {
        Car car = new Car();
        car.setBrand(new Brand(carData.getBrand()));
        car.setModel(new Model(carData.getModel()));
        car.setEngine(new Engine(carData.getEngineName()));
        car.setBoltPattern(new BoltPattern(carData.getBoltPatternSize()));
        car.setConcern(new Concern(carData.getConcern()));
        return car;
    }
}
