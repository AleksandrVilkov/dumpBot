package com.dumpBot.model;

import com.dumpBot.model.enums.AccommodationType;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccommodation {
    private int id;
    private AccommodationType type;
    private Car car;
    private Date createdDate;
    private String clientLogin;
    private int clientId;
    private int price;
    private boolean approved;
    private boolean rejected;
    private boolean topical;
    private String description;
    private List<String> photos;
}
