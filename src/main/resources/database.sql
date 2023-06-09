CREATE USER autodump_user WITH password 'root';
ALTER USER autodump_user SUPERUSER;
CREATE DATABASE autodump OWNER autodump_user;

CREATE TABLE REGION
(
    id           int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         varchar(80) NOT NULL,
    country_code varchar(80) NOT NULL,
    region_id    varchar(80) NOT NULL
);

INSERT INTO public.region (name, country_code, region_id)
VALUES ('Костромская область'::varchar(80), 'RU'::varchar(80), '44'::varchar(80)),
       ('Республика Адыгея (Адыгея)'::varchar(80), 'RU'::varchar(80), '01'::varchar(80)),
       ('Курганская область'::varchar(80), 'RU'::varchar(80), '45'::varchar(80)),
       ('Ямало-Ненецкий автономный округ'::varchar(80), 'RU'::varchar(80), '89'::varchar(80)),
       ('Республика Башкортостан'::varchar(80), 'RU'::varchar(80), '02'::varchar(80)),
       ('Курская область'::varchar(80), 'RU'::varchar(80), '46'::varchar(80)),
       ('Республика Бурятия'::varchar(80), 'RU'::varchar(80), '03'::varchar(80)),
       ('Ленинградская область'::varchar(80), 'RU'::varchar(80), '47'::varchar(80)),
       ('Республика Алтай'::varchar(80), 'RU'::varchar(80), '04'::varchar(80)),
       ('Липецкая область'::varchar(80), 'RU'::varchar(80), '48'::varchar(80)),
       ('Республика Дагестан'::varchar(80), 'RU'::varchar(80), '05'::varchar(80)),
       ('Магаданская область'::varchar(80), 'RU'::varchar(80), '49'::varchar(80)),
       ('Республика Ингушетия'::varchar(80), 'RU'::varchar(80), '06'::varchar(80)),
       ('Кабардино-Балкарская Республика'::varchar(80), 'RU'::varchar(80), '07'::varchar(80)),
       ('Республика Калмыкия'::varchar(80), 'RU'::varchar(80), '08'::varchar(80)),
       ('Карачаево-Черкесская Республика'::varchar(80), 'RU'::varchar(80), '09'::varchar(80)),
       ('Республика Крым'::varchar(80), 'RU'::varchar(80), '91'::varchar(80)),
       ('г. Севастополь'::varchar(80), 'RU'::varchar(80), '92'::varchar(80)),
       ('Московская область'::varchar(80), 'RU'::varchar(80), '50'::varchar(80)),
       ('Мурманская область'::varchar(80), 'RU'::varchar(80), '51'::varchar(80)),
       ('Нижегородская область'::varchar(80), 'RU'::varchar(80), '52'::varchar(80)),
       ('Новгородская область'::varchar(80), 'RU'::varchar(80), '53'::varchar(80)),
       ('Республика Карелия'::varchar(80), 'RU'::varchar(80), '10'::varchar(80)),
       ('Новосибирская область'::varchar(80), 'RU'::varchar(80), '54'::varchar(80)),
       ('Республика Коми'::varchar(80), 'RU'::varchar(80), '11'::varchar(80)),
       ('Омская область'::varchar(80), 'RU'::varchar(80), '55'::varchar(80)),
       ('Иные территории, включая город и космодром Байконур'::varchar(80), 'RU'::varchar(80), '99'::varchar(80)),
       ('Республика Марий Эл'::varchar(80), 'RU'::varchar(80), '12'::varchar(80)),
       ('Оренбургская область'::varchar(80), 'RU'::varchar(80), '56'::varchar(80)),
       ('Республика Мордовия'::varchar(80), 'RU'::varchar(80), '13'::varchar(80)),
       ('Орловская область'::varchar(80), 'RU'::varchar(80), '57'::varchar(80)),
       ('Республика Саха (Якутия)'::varchar(80), 'RU'::varchar(80), '14'::varchar(80)),
       ('Пензенская область'::varchar(80), 'RU'::varchar(80), '58'::varchar(80)),
       ('Республика Северная Осетия - Алания'::varchar(80), 'RU'::varchar(80), '15'::varchar(80)),
       ('Пермский край'::varchar(80), 'RU'::varchar(80), '59'::varchar(80)),
       ('Республика Татарстан (Татарстан)'::varchar(80), 'RU'::varchar(80), '16'::varchar(80)),
       ('Республика Тыва'::varchar(80), 'RU'::varchar(80), '17'::varchar(80)),
       ('Удмуртская Республика'::varchar(80), 'RU'::varchar(80), '18'::varchar(80)),
       ('Республика Хакасия'::varchar(80), 'RU'::varchar(80), '19'::varchar(80)),
       ('Псковская область'::varchar(80), 'RU'::varchar(80), '60'::varchar(80)),
       ('Ростовская область'::varchar(80), 'RU'::varchar(80), '61'::varchar(80)),
       ('Рязанская область'::varchar(80), 'RU'::varchar(80), '62'::varchar(80)),
       ('Самарская область'::varchar(80), 'RU'::varchar(80), '63'::varchar(80)),
       ('Чеченская Республика'::varchar(80), 'RU'::varchar(80), '20'::varchar(80)),
       ('Саратовская область'::varchar(80), 'RU'::varchar(80), '64'::varchar(80)),
       ('Чувашская Республика - Чувашия'::varchar(80), 'RU'::varchar(80), '21'::varchar(80)),
       ('Сахалинская область'::varchar(80), 'RU'::varchar(80), '65'::varchar(80)),
       ('Алтайский край'::varchar(80), 'RU'::varchar(80), '22'::varchar(80)),
       ('Свердловская область'::varchar(80), 'RU'::varchar(80), '66'::varchar(80)),
       ('Краснодарский край'::varchar(80), 'RU'::varchar(80), '23'::varchar(80)),
       ('Смоленская область'::varchar(80), 'RU'::varchar(80), '67'::varchar(80)),
       ('Красноярский край'::varchar(80), 'RU'::varchar(80), '24'::varchar(80)),
       ('Тамбовская область'::varchar(80), 'RU'::varchar(80), '68'::varchar(80)),
       ('Приморский край'::varchar(80), 'RU'::varchar(80), '25'::varchar(80)),
       ('Тверская область'::varchar(80), 'RU'::varchar(80), '69'::varchar(80)),
       ('Ставропольский край'::varchar(80), 'RU'::varchar(80), '26'::varchar(80)),
       ('Хабаровский край'::varchar(80), 'RU'::varchar(80), '27'::varchar(80)),
       ('Амурская область'::varchar(80), 'RU'::varchar(80), '28'::varchar(80)),
       ('Архангельская область'::varchar(80), 'RU'::varchar(80), '29'::varchar(80)),
       ('Томская область'::varchar(80), 'RU'::varchar(80), '70'::varchar(80)),
       ('Тульская область'::varchar(80), 'RU'::varchar(80), '71'::varchar(80)),
       ('Тюменская область'::varchar(80), 'RU'::varchar(80), '72'::varchar(80)),
       ('Ульяновская область'::varchar(80), 'RU'::varchar(80), '73'::varchar(80)),
       ('Астраханская область'::varchar(80), 'RU'::varchar(80), '30'::varchar(80)),
       ('Челябинская область'::varchar(80), 'RU'::varchar(80), '74'::varchar(80)),
       ('Белгородская область'::varchar(80), 'RU'::varchar(80), '31'::varchar(80)),
       ('Забайкальский край'::varchar(80), 'RU'::varchar(80), '75'::varchar(80)),
       ('Брянская область'::varchar(80), 'RU'::varchar(80), '32'::varchar(80)),
       ('Ярославская область'::varchar(80), 'RU'::varchar(80), '76'::varchar(80)),
       ('Владимирская область'::varchar(80), 'RU'::varchar(80), '33'::varchar(80)),
       ('г. Москва'::varchar(80), 'RU'::varchar(80), '77'::varchar(80)),
       ('Волгоградская область'::varchar(80), 'RU'::varchar(80), '34'::varchar(80)),
       ('г. Санкт-Петербург'::varchar(80), 'RU'::varchar(80), '78'::varchar(80)),
       ('Вологодская область'::varchar(80), 'RU'::varchar(80), '35'::varchar(80)),
       ('Еврейская автономная область'::varchar(80), 'RU'::varchar(80), '79'::varchar(80)),
       ('Воронежская область'::varchar(80), 'RU'::varchar(80), '36'::varchar(80)),
       ('Ивановская область'::varchar(80), 'RU'::varchar(80), '37'::varchar(80)),
       ('Иркутская область'::varchar(80), 'RU'::varchar(80), '38'::varchar(80)),
       ('Калининградская область'::varchar(80), 'RU'::varchar(80), '39'::varchar(80)),
       ('Ненецкий автономный округ'::varchar(80), 'RU'::varchar(80), '83'::varchar(80)),
       ('Калужская область'::varchar(80), 'RU'::varchar(80), '40'::varchar(80)),
       ('Камчатский край'::varchar(80), 'RU'::varchar(80), '41'::varchar(80)),
       ('Кемеровская область'::varchar(80), 'RU'::varchar(80), '42'::varchar(80)),
       ('Ханты-Мансийский автономный округ - Югра'::varchar(80), 'RU'::varchar(80), '86'::varchar(80)),
       ('Кировская область'::varchar(80), 'RU'::varchar(80), '43'::varchar(80)),
       ('Чукотский автономный округ'::varchar(80), 'RU'::varchar(80), '87'::varchar(80)),
       ('Брестская область'::varchar(80), 'BY'::varchar(80), '1'::varchar(80)),
       ('Витебская область'::varchar(80), 'BY'::varchar(80), '2'::varchar(80)),
       ('Гомельская область'::varchar(80), 'BY'::varchar(80), '3'::varchar(80)),
       ('Гродненская область'::varchar(80), 'BY'::varchar(80), '4'::varchar(80)),
       ('Минская область'::varchar(80), 'BY'::varchar(80), '5'::varchar(80)),
       ('Могилёвская область'::varchar(80), 'BY'::varchar(80), '6'::varchar(80)),
       ('г. Минск'::varchar(80), 'BY'::varchar(80), '7'::varchar(80));

CREATE TABLE CAR
(
    id           int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    concern      varchar(80),
    brand        varchar(80),
    model        varchar(80),
    engine       varchar(80),
    engine_type  varchar(80),
    transmission varchar(80),
    bolt_pattern varchar(80),
    year_from    date,
    year_to      date,
    class        varchar(2)

);

INSERT INTO public.car (concern, brand, model, engine, bolt_pattern, year_from, year_to, class)
VALUES ('PSA', 'Peugeot', '107', '1KR-FE', null, null, null, null),
       ('PSA', 'Peugeot', '206', 'TU3JP', null, null, null, null),
       ('PSA', 'Peugeot', '206', 'TU3A', null, null, null, null),
       ('PSA', 'Peugeot', '206', 'TU5JP4', null, null, null, null),
       ('PSA', 'Peugeot', '206', 'TU1JP', null, null, null, null),
       ('PSA', 'Peugeot', '206', 'EW10J4', null, null, null, null),
       ('PSA', 'Peugeot', '206', 'DV4TD', null, null, null, null),
       ('PSA', 'Peugeot', '206', 'DW10TD', null, null, null, null),
       ('PSA', 'Peugeot', '207', 'TU3A', null, null, null, null),
       ('PSA', 'Peugeot', '207', 'ET3J4', null, null, null, null),
       ('PSA', 'Peugeot', '207', 'EP6', null, null, null, null),
       ('PSA', 'Peugeot', '207', 'EP6C', null, null, null, null),
       ('PSA', 'Peugeot', '207', 'EP6DT', null, null, null, null),
       ('PSA', 'Peugeot', '208', 'EP6', null, null, null, null),
       ('PSA', 'Peugeot', '208', 'EP6C', null, null, null, null),
       ('PSA', 'Peugeot', '208', 'DV6DTED', null, null, null, null),
       ('PSA', 'Peugeot', '208', 'DV6', null, null, null, null),
       ('PSA', 'Peugeot', '307', 'ET3J4', null, null, null, null),
       ('PSA', 'Peugeot', '307', 'DV6ATED4', null, null, null, null),
       ('PSA', 'Peugeot', '307', 'TU5JP4', null, null, null, null),
       ('PSA', 'Peugeot', '307', 'EW10A', null, null, null, null),
       ('PSA', 'Peugeot', '307', 'DW10BTED4', null, null, null, null),
       ('PSA', 'Peugeot', '308', '9HZ', null, null, null, null),
       ('PSA', 'Peugeot', '308', 'EP6', null, null, null, null),
       ('PSA', 'Peugeot', '308', 'EP6C', null, null, null, null),
       ('PSA', 'Peugeot', '308', 'EP6DT', null, null, null, null),
       ('PSA', 'Peugeot', '308', 'EP6CDT', null, null, null, null),
       ('PSA', 'Peugeot', '308', 'DW10BTED4', null, null, null, null),
       ('PSA', 'Peugeot', '308', 'DV6C', null, null, null, null),
       ('PSA', 'Peugeot', '308', 'N6A-C', null, null, null, null),
       ('PSA', 'Peugeot', '308', '5FE-J', null, null, null, null),
       ('PSA', 'Peugeot', '308', '5FS-9', null, null, null, null),
       ('PSA', 'Peugeot', '607', 'EW12J4', null, null, null, null),
       ('PSA', 'Peugeot', '607', 'ES9IA', null, null, null, null),
       ('PSA', 'Peugeot', '607', 'DW12ATED4', null, null, null, null),
       ('PSA', 'Peugeot', '607', 'ES9J4S', null, null, null, null),
       ('PSA', 'Peugeot', '301', 'EB2M', null, null, null, null),
       ('PSA', 'Peugeot', '301', 'DV6DTED', null, null, null, null),
       ('PSA', 'Peugeot', '301', 'EC5', null, null, null, null),
       ('PSA', 'Peugeot', '407', 'EW7A', null, null, null, null),
       ('PSA', 'Peugeot', '407', 'EW10A', null, null, null, null),
       ('PSA', 'Peugeot', '407', 'EW12J4', null, null, null, null),
       ('PSA', 'Peugeot', '407', 'ES9A', null, null, null, null),
       ('PSA', 'Peugeot', '407', 'EW7A', null, null, null, null),
       ('PSA', 'Peugeot', '407', 'EW12A', null, null, null, null),
       ('PSA', 'Peugeot', '408', 'DV6CM', null, null, null, null),
       ('PSA', 'Peugeot', '408', 'TU5JP4', null, null, null, null),
       ('PSA', 'Peugeot', '408', 'EP6', null, null, null, null),
       ('PSA', 'Peugeot', '408', 'EP6C', null, null, null, null),
       ('PSA', 'Peugeot', '408', 'EP6DT', null, null, null, null),
       ('PSA', 'Peugeot', '408', 'EP6CDT', null, null, null, null),
       ('PSA', 'Peugeot', '508', 'EP6C', null, null, null, null),
       ('PSA', 'Peugeot', '508', 'EP6DT', null, null, null, null),
       ('PSA', 'Peugeot', '508', 'DW10BTED4', null, null, null, null),
       ('PSA', 'Peugeot', '508', 'DW12C', null, null, null, null),
       ('PSA', 'Peugeot', 'RCZ', 'EP6DT', null, null, null, null),
       ('PSA', 'Peugeot', 'RCZ', 'EP6CDT', null, null, null, null),
       ('PSA', 'Peugeot', 'RCZ', 'EP6CDTX', null, null, null, null),
       ('PSA', 'Citroen', 'C1', '1KR-FE', null, null, null, null),
       ('PSA', 'Citroen', 'C1', '1KR-FE', null, null, null, null),
       ('PSA', 'Citroen', 'C2', 'TU1JP ', null, null, null, null),
       ('PSA', 'Citroen', 'C2', 'TU3JP', null, null, null, null),
       ('PSA', 'Citroen', 'C2', 'DV4TD', null, null, null, null),
       ('PSA', 'Citroen', 'C2', 'DV6TED4', null, null, null, null),
       ('PSA', 'Citroen', 'C2', 'TU5JP4', null, null, null, null),
       ('PSA', 'Citroen', 'C2', 'TU5JP4S', null, null, null, null),
       ('PSA', 'Citroen', 'C3', 'TU3A', null, null, null, null),
       ('PSA', 'Citroen', 'C3', 'EP3', null, null, null, null),
       ('PSA', 'Citroen', 'C3', 'EP6C', null, null, null, null),
       ('PSA', 'Citroen', 'C3', 'TU1JP', null, null, null, null),
       ('PSA', 'Citroen', 'C3', 'ET3J4', null, null, null, null),
       ('PSA', 'Citroen', 'C3', 'TU5JP4', null, null, null, null),
       ('PSA', 'Citroen', 'C3', 'EB2', null, null, null, null),
       ('PSA', 'Citroen', 'C3', 'EB2DT', null, null, null, null),
       ('PSA', 'Citroen', 'C3', 'DV6D', null, null, null, null),
       ('PSA', 'Citroen', 'C3', 'EP6', null, null, null, null),
       ('PSA', 'Citroen', 'C4', 'ET3J4', null, null, null, null),
       ('PSA', 'Citroen', 'C4', 'TU5JP4', null, null, null, null),
       ('PSA', 'Citroen', 'C4', 'EW10J4', null, null, null, null),
       ('PSA', 'Citroen', 'C4', 'DV6ATED4', null, null, null, null),
       ('PSA', 'Citroen', 'C4', 'EP6', null, null, null, null),
       ('PSA', 'Citroen', 'C4', 'EP6DT	*8DW10B', null, null, null, null),
       ('PSA', 'Citroen', 'C4', 'DW10BTED4', null, null, null, null),
       ('PSA', 'Citroen', 'C4', 'DV6C', null, null, null, null),
       ('PSA', 'Citroen', 'C4', 'EC5', null, null, null, null),
       ('PSA', 'Citroen', 'C4', 'EB2DT', null, null, null, null),
       ('PSA', 'Citroen', 'C4 Picasso', 'DV6C', null, null, null, null),
       ('PSA', 'Citroen', 'C4 Picasso', 'EP6CDT', null, null, null, null),
       ('PSA', 'Citroen', 'C4 Picasso', 'EP6', null, null, null, null),
       ('PSA', 'Citroen', 'C4 Picasso', 'EW7A', null, null, null, null),
       ('PSA', 'Citroen', 'C4 Picasso', 'EW10A', null, null, null, null),
       ('PSA', 'Citroen', 'C5', 'EW7J4', null, null, null, null),
       ('PSA', 'Citroen', 'C5', 'EW10A', null, null, null, null),
       ('PSA', 'Citroen', 'C5', 'ES9J4S', null, null, null, null),
       ('PSA', 'Citroen', 'C5', 'EP6C', null, null, null, null),
       ('PSA', 'Citroen', 'C5', 'EP6CDT', null, null, null, null),
       ('PSA', 'Citroen', 'C5', 'EW7A', null, null, null, null),
       ('PSA', 'Citroen', 'C5', 'EW10A', null, null, null, null),
       ('PSA', 'Citroen', 'C5', 'DW10BTED4', null, null, null, null),
       ('PSA', 'Citroen', 'C5', 'DW12C', null, null, null, null),
       ('PSA', 'Citroen', 'C5', 'EW7J4', null, null, null, null),
       ('PSA', 'Citroen', 'DS4', 'EP6C', null, null, null, null),
       ('PSA', 'Citroen', 'DS4', 'EP6CDTMD', null, null, null, null),
       ('PSA', 'Citroen', 'DS4', 'EP6DTX', null, null, null, null),
       ('PSA', 'Citroen', 'DS4', 'DW10CTED4', null, null, null, null),
       ('PSA', 'Citroen', 'DS5', 'EP6CDTMD', null, null, null, null),
       ('PSA', 'Citroen', 'DS5', 'EP6DTX', null, null, null, null),
       ('PSA', 'Citroen', 'DS5', 'DW10CTED4', null, null, null, null),
       ('PSA', 'Citroen', 'DS3', 'EB2F', null, null, null, null),
       ('PSA', 'Citroen', 'DS3', 'EP3', null, null, null, null),
       ('PSA', 'Citroen', 'DS3', 'EP6C', null, null, null, null),
       ('PSA', 'Citroen', 'DS3', 'EP6DT', null, null, null, null),
       ('PSA', 'Citroen', 'DS3', 'EP6CDT', null, null, null, null),
       ('PSA', 'Citroen', 'Berlingo', 'EB2DT', null, null, null, null),
       ('PSA', 'Citroen', 'Berlingo', 'TU5JP4', null, null, null, null),
       ('PSA', 'Citroen', 'Berlingo', 'DV6TED4', null, null, null, null),
       ('PSA', 'Citroen', 'Xsara', 'TU5JP4', null, null, null, null),
       ('PSA', 'Citroen', 'Xsara', 'EW10J4', null, null, null, null),
       ('PSA', 'Citroen', 'Xsara', 'DV6TED4', null, null, null, null);

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

CREATE TABLE user_accommodation
(
    id           int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created_date date                       NOT NULL,
    type         varchar(80)                NOT NULL,
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
    telegram_id           varchar(225) NOT NULL,
    user_accommodation_id int          NOT NULL REFERENCES user_accommodation (id)
);
CREATE TABLE user_accommodation_car
(
    car_accommodation_id             int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    car_id           varchar(225) NOT NULL,
    user_accommodation_id int          NOT NULL REFERENCES user_accommodation (id)
);