CREATE TABLE REGION
(
    id        int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name      varchar(80) NOT NULL,
    region_id varchar(80) NOT NULL
);
CREATE TABLE CAR
(
    id           int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    concern      varchar(80),
    brand        varchar(80),
    model        varchar(80),
    engine       varchar(80),
    bolt_pattern varchar(80),
    year_from    date,
    year_to      date,
    class        varchar(2)

);
CREATE TABLE CLIENT
(
    id               int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created_date     date                       NOT NULL,
    role             varchar(80)                NOT NULL,
    login            varchar(80)                NOT NULL UNIQUE,
    region_id        int REFERENCES REGION (id) NOT NULL,
    user_name        varchar(80)                NOT NULL,
    client_action    varchar(80),
    waiting_messages boolean                    NOT NULL,
    carid            int REFERENCES CAR (id)    NOT NULL,
    last_callBack    varchar(1000)
);

CREATE TABLE TEMPDATA
(
    id          int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    token       varchar(80) NOT NULL UNIQUE,
    createdDate date        NOT NULL,
    callback    varchar(1000)
);

CREATE TABLE CURRENCY
(
    id   int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(80) NOT NULL
);

CREATE TABLE ANNOUNCEMENT
(
    id          int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    createdDate date                         NOT NULL,
    clientLogin varchar(80)                  NOT NULL,
    clientId    int REFERENCES CLIENT (id)   NOT NULL,
    carId       int REFERENCES CAR (id)      NOT NULL,
    price       int,
    currencyId  int REFERENCES CURRENCY (id) NOT NULL,
    approved    bool                         NOT NULL,
    rejected    bool                         NOT NULL,
    topical     bool                         NOT NULL,
    description varchar(1000)                NOT NULL
);

CREATE TABLE user_accommodation
(
    id           int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created_date date                       NOT NULL,
    type varchar(80)                NOT NULL,
    client_login varchar(80)                NOT NULL,
    client_id    int REFERENCES CLIENT (id) NOT NULL,
    min_price    int,
    max_price    int,
    approved     bool                       NOT NULL,
    rejected     bool                       NOT NULL,
    topical      bool                       NOT NULL,
    description  varchar(1000)              NOT NULL
);

CREATE TABLE user_accommodation_photo
(
    photo_id              int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    telegram_id      varchar(225) NOT NULL,
    user_accommodation_id int NOT NULL REFERENCES user_accommodation (id)
);
