package com.dumpBot.processor.msgProcessor.process.ready;

import com.dumpBot.common.Util;
import com.dumpBot.model.*;
import com.dumpBot.model.enums.AccommodationType;
import com.dumpBot.model.enums.Action;

import java.util.Date;

public class ReadyUtils {



    public static UserAccommodation createUserAccommodation(LastCallback lastCallback, User user, Car car, City city) {
       String description = user.getClientAction().equalsIgnoreCase(Action.SEARCH.name()) ?
               createSearchText(lastCallback, user, car, city) :
               createSaleText(lastCallback, user, car, city);

        return UserAccommodation.builder()
                .type(Util.findEnumConstant(AccommodationType.class,user.getClientAction()))
                .createdDate(new Date())
                .clientLogin(user.getLogin())
                .clientId(user.getId())
                .price(Integer.parseInt(lastCallback.getPrice()))
                .approved(false)
                .rejected(false)
                .topical(true)
                .description(description)
                .photos(lastCallback.getPhotos())
                .build();
    }

    private static String createSaleText(LastCallback lastCallback, User user, Car car, City city) {

        return "Продам: " + lastCallback.getDescription() + "\n" +
                "Концерн: " + car.getConcern().getName() + "\n" +
                "Бренд: " + car.getBrand().getName() + "\n" +
                "Модель: " + car.getModel().getName() + "\n" +
                "Цена: " + lastCallback.getPrice() + "\n" +
                "Писать: @" + user.getUserName() + "\n" +
                "Местонахождение: " + city.getName();
    }

    private static String createSearchText(LastCallback lastCallback, User user, Car car, City city) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Куплю: ").append(lastCallback.getDescription()).append("\n");
        if (car != null) {
            stringBuilder.append("Концерн: ")
                    .append(car.getConcern().getName()).append("\n")
                    .append("Бренд: ")
                    .append(car.getBrand().getName())
                    .append("\n")
                    .append("Модель: ")
                    .append(car.getModel().getName()).append("\n");
        }
        if (lastCallback.getPrice() != null && !lastCallback.getPrice().equals("")) {
            stringBuilder.append("Интересна цена до: ")
                    .append(lastCallback.getPrice()).append("\n");
        }
        stringBuilder.append("Писать: @")
                .append(user.getUserName())
                .append("\n")
                .append("Местонахождение: ")
                .append(city.getName());

        return stringBuilder.toString();
    }

}
