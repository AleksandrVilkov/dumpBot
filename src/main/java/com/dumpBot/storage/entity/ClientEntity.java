package com.dumpBot.storage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "CLIENT")
@NoArgsConstructor
@Getter
@Setter
public class ClientEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "role")
    private String role;
    @Column(name = "login")
    private String login;
    @Column(name = "regionId")
    private int regionId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "carid")
    private int carid;
    @Column(name = "waiting_messages")
    private boolean waitingMessages;
    @Column(name = "clientAction")
    private String clientAction;
    @Column(name = "lastCallback")
    private String lastCallback;
}
