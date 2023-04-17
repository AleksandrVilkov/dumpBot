package com.dumpBot.storage.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "TEMPDATA")
@NoArgsConstructor
@Getter
@Setter
public class TempData {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "token")
    private String token;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "callback")
    private String callback;

}
