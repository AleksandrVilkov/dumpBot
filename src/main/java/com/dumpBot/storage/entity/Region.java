package com.dumpBot.storage.entity;

import com.dumpBot.model.City;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "region")
@NoArgsConstructor
@Getter
@Setter
public class Region {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "regionId")
    private String regionId;

    public City toCity() {
        City city = new City();
        city.setRegionId(Integer.parseInt(String.valueOf(this.getId())));
        city.setName(this.getName());
        return city;
    }
}
