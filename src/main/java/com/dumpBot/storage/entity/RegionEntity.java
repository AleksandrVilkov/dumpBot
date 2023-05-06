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
public class RegionEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "region_id")
    private String regionId;

    public City toCity() {
        City city = new City();
        city.setRegionId((String.valueOf(this.getId())));
        city.setName(this.getName());
        return city;
    }
}
