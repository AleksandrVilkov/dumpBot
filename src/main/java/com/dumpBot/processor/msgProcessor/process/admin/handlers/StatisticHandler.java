package com.dumpBot.processor.msgProcessor.process.admin.handlers;

import com.dumpBot.model.Car;
import com.dumpBot.model.User;
import com.dumpBot.model.UserAccommodation;
import com.dumpBot.model.enums.AccommodationType;
import com.dumpBot.resources.Resources;
import com.dumpBot.storage.IAccommodationStorage;
import com.dumpBot.storage.ICarStorage;
import com.dumpBot.storage.IUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;

@Component
public class StatisticHandler implements TextMsgHandler {

    @Autowired
    IAccommodationStorage accommodationStorage;
    @Autowired
    IUserStorage userStorage;
    @Autowired
    ICarStorage carStorage;
    @Autowired
    Resources resources;

    @Override
    public List<SendMessage> execute(Message message) {
        List<User> users = userStorage.getAllUsers();
        List<SendMessage> result = new ArrayList<>();
        result.add(calcUserStat(message, users));
        result.add(calcCarsStat(message, users));
        result.add(calcAccommodationStat(message));
        return result;
    }

    private SendMessage calcUserStat(Message message, List<User> users) {
        String userStatistic = resources.getMsgs().getAdmin().getStatistics().getRegistered() + ": " + users.size();
        return new SendMessage(String.valueOf(message.getFrom().getId()), userStatistic);
    }

    private SendMessage calcCarsStat(Message message, List<User> users) {
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
        carStatisticStringBuilder.append(resources.getMsgs().getAdmin().getStatistics().getCars()).append(":\n");
        for (Map.Entry entry : carCount.entrySet()) {
            Car car = carStorage.findCarById((Integer) entry.getKey());
            carStatisticStringBuilder.append(car.getBrand().getName())
                    .append(" ")
                    .append(car.getModel().getName())
                    .append(" ")
                    .append(resources.getMsgs().getAdmin().getStatistics().getInQuantity())
                    .append(" ")
                    .append(entry.getValue())
                    .append(resources.getMsgs().getAdmin().getStatistics().getThings())
                    .append(";\n");
        }
        String carStatistic = carStatisticStringBuilder.toString();
        return new SendMessage(String.valueOf(message.getFrom().getId()), carStatistic);
    }

    private SendMessage calcAccommodationStat(Message message) {
        List<UserAccommodation> userAccommodations = accommodationStorage.getAll();
        int count = userAccommodations.size();

        int countSearch = 0;
        int approvedSearch = 0;
        int rejectedSearch = 0;

        int countSale = 0;
        int approvedSale = 0;
        int rejectedSale = 0;

        int topical = 0;
        for (UserAccommodation userAccommodation : userAccommodations) {
            if (userAccommodation.getType().equals(AccommodationType.SEARCH)) {
                countSearch++;
                if (userAccommodation.isApproved()) {
                    approvedSearch++;
                }
                if (userAccommodation.isRejected()) {
                    rejectedSearch++;
                }
            }
            if (userAccommodation.getType().equals(AccommodationType.SALE)) {
                countSale++;
                if (userAccommodation.isApproved()) {
                    approvedSale++;
                }
                if (userAccommodation.isRejected()) {
                    rejectedSale++;
                }
            }
            if (userAccommodation.isTopical()) {
                topical++;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("СТАТИСТИКА\nВсего запросов: ")
                .append(userAccommodations.size())
                .append(" шт.\n")

                .append("Из ниx: \n - запросов на поиск запчастей - ")
                .append(countSearch)
                .append(" шт.\n")
                .append("- на продажу - ")
                .append(countSale)
                .append("\n\n")
                .append("Одобрено и размещено в канале :\n- ")
                .append(approvedSearch)
                .append(" запросов на поиск\n- ")
                .append(approvedSale)
                .append(" объявлений о продаже.\n\n")
                .append("Отклонено: \n- ")
                .append(rejectedSearch)
                .append(" запросов на поиск;\n- ")
                .append(rejectedSale)
                .append(" объявлений.\n")
                .append("На текущий момент не рассмотрено: ")
                .append(topical)
                .append(" заявкок.");
        return new SendMessage(String.valueOf(message.getFrom().getId()), stringBuilder.toString());
    }
}
