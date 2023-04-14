package com.dumpBot.storage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "CAR")
@Getter
@Setter
public class Car {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "concern")
    private String concern;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "engine")
    private String engine;

    @Column(name = "boltPattern")
    private String boltPattern;

    @Column(name = "yearFrom")
    private Date yearFrom;

    @Column(name = "yearTo")
    private Date yearTo;

    @Column(name = "class")
    private String autoClass;
}
