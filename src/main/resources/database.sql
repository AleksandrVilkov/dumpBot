CREATE USER autodump_user WITH password 'root';
ALTER USER autodump_user SUPERUSER;
CREATE DATABASE autodump OWNER autodump_user;

CREATE TABLE REGION
(
    id   int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(80) NOT NULL,
    regionId varchar(80) NOT NULL
);

CREATE TABLE CLIENT
(
    id          int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    createdDate date                       NOT NULL,
    role        varchar(80)                NOT NULL,
    login       varchar(80)         NOT NULL UNIQUE,
    regionId    int REFERENCES REGION (id) NOT NULL,
    carid int REFERENCES CAR (id) NOT NULL
);

CREATE TABLE TEMPDATA
(
 id          int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
token varchar(80) NOT NULL UNIQUE,
createdDate date NOT NULL,
callback varchar(1000)
);

CREATE TABLE CURRENCY
(
    id   int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(80) NOT NULL
);
CREATE TABLE CAR
(
    id          int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    concern     varchar(80),
    brand       varchar(80),
    model       varchar(80),
    engine      varchar(80),
    boltPattern varchar(80),
    yearFrom    date,
    yearTo      date,
    class       varchar(2)

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
    description varchar(255)                 NOT NULL
);

CREATE TABLE SEARCHTERMS
(
    id                int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    createdDate       date                       NOT NULL,
    clientLogin       varchar(80)                NOT NULL,
    clientId          int REFERENCES CLIENT (id) NOT NULL,
    carId             int REFERENCES CAR (id)    NOT NULL,
    minPrice          int,
    maxPrice          int,
    desiredPartNumber varchar(80),
    currencyId        int REFERENCES CURRENCY (id),
    approved          bool                       NOT NULL,
    rejected          bool                       NOT NULL,
    topical           bool                       NOT NULL,
    description       varchar(255)               NOT NULL
);
