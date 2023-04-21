package com.dumpBot.storage.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user_accommodation")
@NoArgsConstructor
@Getter
@Setter
public class UserAccommodationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "client_login")
    private String clientLogin;

    @Column(name = "client_id")
    private int clientId;

    @Column(name = "min_price")
    private int minPrice;

    @Column(name = "max_price")
    private int maxPrice;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "rejected")
    private boolean rejected;

    @Column(name = "topical")
    private boolean topical;

    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "userAccommodationEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<PhotoEntity> photo;

}
