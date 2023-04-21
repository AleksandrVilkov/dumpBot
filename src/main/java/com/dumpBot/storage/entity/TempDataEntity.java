package com.dumpBot.storage.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tempdata")
@NoArgsConstructor
@Getter
@Setter
public class TempDataEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "token")
    private String token;
    @Column(name = "createddate")
    private Date createdDate;
    @Column(name = "callback", length = 1000)
    private String callback;
}
