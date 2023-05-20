package com.dumpBot.processor.msgProcessor.process.admin.handlers;

import com.dumpBot.model.Car;
import com.dumpBot.model.User;
import com.dumpBot.storage.IAccommodationStorage;
import com.dumpBot.storage.ICarStorage;
import com.dumpBot.storage.IUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StatisticHandler implements TextMsgHandler {

    @Autowired
    IAccommodationStorage accommodationStorage;
    @Autowired
    IUserStorage userStorage;
    @Autowired
    ICarStorage carStorage;

    //TODO подключить ресурсы
    @Override
    public List<SendMessage> execute(Message message) {
        List<User> users = userStorage.getAllUsers();
        String userStatistic = "На текущий момент зарегистрировано " + users.size() + " пользователей";
        Map<Integer, Integer> carCount = new HashMap<>(); //id автомобиля и его количество
        for (User user : users) {
            int carId = user.getCarId();
            if (carCount.containsKey(carId)) {
                int count = carCount.get(carId);
                count++;
                carCount.put(carId, count);
            } else {
                carCount.put(carId, 1);
            }
        }
        StringBuilder carStatisticStringBuilder = new StringBuilder();
        carStatisticStringBuilder.append("Прутствуют следующие автомобили:\n");
        for (Map.Entry entry : carCount.entrySet()) {
            Car car = carStorage.findCarById((Integer) entry.getKey());
            carStatisticStringBuilder.append(car.getBrand().getName())
                    .append(" ")
                    .append(car.getModel().getName())
                    .append(" в количестве ")
                    .append(entry.getValue())
                    .append("шт;\n");
        }
        String carStatistic = carStatisticStringBuilder.toString();
        SendMessage usr = new SendMessage(String.valueOf(message.getFrom().getId()), userStatistic);
        SendMessage cars = new SendMessage(String.valueOf(message.getFrom().getId()), carStatistic);
        List<SendMessage> result = new ArrayList<>();
        result.add(usr);
        result.add(cars);
        return result;
    }
}
