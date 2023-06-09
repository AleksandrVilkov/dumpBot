package com.dumpBot.processor.msgProcessor.process.ready;

import com.dumpBot.common.Util;
import com.dumpBot.model.*;
import com.dumpBot.model.enums.AccommodationType;
import com.dumpBot.model.enums.Action;

import java.util.*;

public class ReadyUtils {



    public static UserAccommodation createUserAccommodation(LastCallback lastCallback, User user, List<Car> cars, City city) {
       String description = user.getClientAction().equalsIgnoreCase(Action.SEARCH.name()) ?
               createSearchText(lastCallback, user, city) :
               createSaleText(lastCallback, user, cars, city);

       List<String> carIds = new ArrayList<>();
       for (Car car: cars) {
           carIds.add(String.valueOf(car.getId()));
       }

       int price = lastCallback.getPrice().isEmpty() ? 0 :Integer.parseInt(lastCallback.getPrice());
        return UserAccommodation.builder()
                .type(Util.findEnumConstant(AccommodationType.class,user.getClientAction()))
                .createdDate(new Date())
                .clientLogin(user.getLogin())
                .clientId(user.getId())
                .price(price)
                .approved(false)
                .rejected(false)
                .topical(true)
                .description(description)
                .photos(lastCallback.getPhotos())
                .carsId(carIds)
                .build();
    }

    private static String createSaleText(LastCallback lastCallback, User user, List<Car> cars, City city) {
        Set<String> carsSet = new LinkedHashSet<>();

        StringBuilder carsText = new StringBuilder("Подходит к автомобилям:\n" );
        for (Car car: cars) {
            carsSet.add(car.getBrand().getName() + " " + car.getModel().getName() + ";\n");
        }
        for (String str: carsSet) {
            carsText.append(str);
        }


        return "Продам: " + lastCallback.getDescription() + "\n" +
                carsText.toString() +
                "Цена: " + lastCallback.getPrice() + "\n" +
                "Писать: @" + user.getUserName() + "\n" +
                "Местонахождение: " + city.getName();
    }

    private static String createSearchText(LastCallback lastCallback, User user, City city) {
        return "Куплю: " + lastCallback.getDescription() + "\n" +
                "Писать: @" +
                user.getUserName() +
                "\n" +
                "Местонахождение: " +
                city.getName();
    }

}
