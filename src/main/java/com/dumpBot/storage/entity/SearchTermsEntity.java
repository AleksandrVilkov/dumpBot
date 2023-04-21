package com.dumpBot.storage.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SEARCHTERMS")
@NoArgsConstructor
@Getter
@Setter
public class SearchTermsEntity {
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

    @OneToMany(mappedBy = "SearchTermsEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<SearchTermsPhotoEntity> photo;

}
