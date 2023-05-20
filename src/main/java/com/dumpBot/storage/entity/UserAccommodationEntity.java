package com.dumpBot.storage.entity;


import com.dumpBot.common.Util;
import com.dumpBot.model.UserAccommodation;
import com.dumpBot.model.enums.AccommodationType;
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

    public UserAccommodation toUserAccommodation() {
        UserAccommodation userAccommodation = new UserAccommodation();
        userAccommodation.setId(this.id);
        //TODO
        //userAccommodation.setCreatedDate((java.sql.Date) this.getCreatedDate());
        userAccommodation.setType(Util.findEnumConstant(AccommodationType.class, this.type));
        userAccommodation.setClientLogin(this.clientLogin);
        userAccommodation.setClientId(this.clientId);
        userAccommodation.setPrice(this.maxPrice);
        userAccommodation.setApproved(this.isApproved());
        userAccommodation.setRejected(this.isRejected());
        userAccommodation.setTopical(this.isTopical());
        userAccommodation.setDescription(this.getDescription());
        return userAccommodation;
    }
}
