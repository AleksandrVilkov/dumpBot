package com.dumpBot.processor.webAppProcessor.process;

import com.dumpBot.model.*;
import com.dumpBot.model.enums.Role;
import com.dumpBot.processor.IUserStorage;
import com.dumpBot.processor.ResourcesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class RegistrationWebAppProcess implements WebAppProcess {

    @Autowired
    IUserStorage storage;
    @Autowired
    ResourcesHelper resourcesHelper;

    @Override
    public boolean processData(Update update, WebAppData webAppData) {
        City city = new City();
        city.setName(webAppData.getCity());
        city.setRegionId(1);
        User user = new User(new Date(),
                Role.USER_ROLE,
                String.valueOf(update.getMessage().getFrom().getId()),
                city,
                convertCarData(webAppData));
        user.setWaitingMessages(false);
        user.setUserName(update.getMessage().getFrom().getUserName());
        return storage.saveUser(user);
    }

    private Car convertCarData(WebAppData webAppData) {
        Car car = new Car();
        car.setBrand(new Brand(webAppData.getBrand()));
        car.setModel(new Model(webAppData.getModel()));
        car.setEngine(new Engine(webAppData.getEngine()));
        car.setBoltPattern(new BoltPattern(webAppData.getBoltPattern()));
        car.setConcern(new Concern(webAppData.getConcern()));
        return car;
    }

    @Override
    public List<SendMessage> prepareAnswer(Update update) {
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        List<SendMessage> result = new ArrayList<>();
        result.add(new SendMessage(userId, resourcesHelper.getResources().getSuccess().getWonderful()));
        result.add(new SendMessage(userId, resourcesHelper.getResources().getSuccess().getSuccessReservation()));
        result.add(new SendMessage(userId, resourcesHelper.getResources().getSuccess().getPossibilities()));
        result.add(new SendMessage(userId, resourcesHelper.getResources().getSuccess().getMenuAccess()));
        return result;
    }

}
