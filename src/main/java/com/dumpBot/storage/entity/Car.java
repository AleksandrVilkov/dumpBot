package com.dumpBot.storage.entity;

import com.dumpBot.model.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "car")
@NoArgsConstructor
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

    public com.dumpBot.model.Car toCarModel() {
        com.dumpBot.model.Car result = new com.dumpBot.model.Car();
        result.setId(id);
        result.setConcern(new Concern(concern));
        result.setModel(new Model(model));
        result.setEngine(new Engine(engine));
        result.setBrand(new Brand(brand));
        result.setBoltPattern(new BoltPattern(boltPattern));
        return result;
    }
}
