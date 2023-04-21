package com.dumpBot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccommodation {
    private int id;
    private AccommodationType type;
    private Date createdDate;
    private String clientLogin;
    private int clientId;
    private int minPrice;
    private int maxPrice;
    private boolean approved;
    private boolean rejected;
    private boolean topical;
    private String description;
    private List<String> photos;
}
