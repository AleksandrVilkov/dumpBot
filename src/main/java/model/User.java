package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class User {
    int id;
    LocalDate createDate;
    Role role;
    String login;
    Region region;

}
