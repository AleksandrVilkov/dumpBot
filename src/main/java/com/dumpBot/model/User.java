package com.dumpBot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String userName;
    private Date createDate;
    private Role role;
    private String login;
    private City region;
    private Car car;
    private boolean waitingMessages;
    private String clientAction;
    private String lastCallback;

    public User(Date createDate, Role role, String login, City region, Car car) {
        this.createDate = createDate;
        this.role = role;
        this.login = login;
        this.region = region;
        this.car = car;
    }
}
