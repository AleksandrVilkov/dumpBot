package com.dumpBot.storage.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "SEARCHTERMS")
@NoArgsConstructor
@Getter
@Setter
public class SearchTerms {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "clientLogin")
    private String clientLogin;

    @Column(name = "clientId")
    private int clientId;

    @Column(name = "carId")
    private int carId;

    @Column(name = "minPrice")
    private int minPrice;

    @Column(name = "maxPrice")
    private int maxPrice;

    @Column(name = "desiredPartNumber")
    private String desiredPartNumber;

    @Column(name = "currencyId")
    private int currencyId;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "rejected")
    private boolean rejected;

    @Column(name = "topical")
    private boolean topical;

    @Column(name = "description")
    private String description;
}
