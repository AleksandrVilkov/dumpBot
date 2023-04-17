package com.dumpBot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    int id;
    LocalDate createDate;
    Role role;
    String login;
    City region;
    Car car;

    public User(LocalDate createDate, Role role, String login, City region, Car car) {
        this.createDate = createDate;
        this.role = role;
        this.login = login;
        this.region = region;
        this.car = car;
    }
}
