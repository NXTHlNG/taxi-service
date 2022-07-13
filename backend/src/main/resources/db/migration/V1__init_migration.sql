drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence;

drop sequence if exists tariffs_seq;
create sequence tariffs_seq;

drop sequence if exists users_seq;
create sequence users_seq;

drop sequence if exists cars_seq;
create sequence cars_seq;

drop sequence if exists orders_seq;
create sequence orders_seq;

drop sequence if exists roles_seq;
create sequence roles_seq;

drop sequence if exists draftorders_seq;
create sequence draftorders_seq;

drop sequence if exists driver_req_seq;
create sequence driver_req_seq;

drop type if exists "orderStatus";
create type "orderStatus" as enum ('PREPARING', 'LOOKING_FOR_DRIVER', 'IN_PROGRESS', 'WAITING', 'DONE', 'CANCELLED');

drop type if exists "driverStatus";
create type "driverStatus" as enum ('NOT_ACTIVE', 'LOOKING_FOR_ORDER', 'WAITING', 'ON_THE_WAY');

drop type if exists "roleName";
create type "roleName" as enum ('ROLE_CUSTOMER', 'ROLE_DRIVER', 'ROLE_ADMIN');

drop type if exists "paymentMethod";
create type "paymentMethod" as enum ('CARD', 'CASH');

drop type if exists "DriverRequestStatus";
create type "DriverRequestStatus" as enum ('PENDING', 'ACCEPTED', 'REJECTED');

drop table if exists Tariffs;
create table Tariffs
(
    id              bigint default NEXTVAL('tariffs_seq') not null,
    rank            bigint                                not null,
    name            varchar(255)                          not null,
    min_price       double precision                      not null,
    minute_price    double precision                      not null,
    kilometer_price double precision                      not null,
    waiting_price   double precision                      not null,
    free_waiting    integer                               not null,
    commission      double precision                      not null,
    primary key (id)
);

drop table if exists Users;
create table Users
(
    id         bigint  default NEXTVAL('users_seq') not null,
    first_name varchar(255)                         not null,
    last_name  varchar(255)                         not null,
    username   varchar(255) unique                  not null,
    email      varchar(255) unique                  not null,
    password   varchar(255)                         not null,
    is_active  boolean default true,
    primary key (id)
);

drop table if exists Drivers;
create table Drivers
(
    id           bigint not null,
    patronymic   varchar(255),
    license      varchar(255),
    rating       double precision default 5,
    status       "driverStatus"   default 'NOT_ACTIVE',
    balance      double precision,
    tariff_id    bigint,
    geo_position varchar(255),
    primary key (id),
    foreign key (id) references Users,
    foreign key (tariff_id) references Tariffs
);

drop table if exists Customers;
create table Customers
(
    id bigint not null,
    primary key (id),
    foreign key (id) references Users
);

drop table if exists Roles;
create table Roles
(
    id   bigint default NEXTVAL('roles_seq') not null,
    name "roleName"                          not null,
    primary key (id)
);

drop table if exists User_roles;
create table User_roles
(
    role_id bigint not null,
    user_id bigint not null,
    foreign key (role_id) references Roles,
    foreign key (user_id) references Users
);

drop table if exists Cars;
create table Cars
(
    id        bigint default NEXTVAL('cars_seq') not null,
    name      varchar(255)                       not null,
    color     varchar(255)                       not null,
    year      int                                not null,
    plate     varchar(255)                       not null,
    driver_id bigint,
    primary key (id),
    foreign key (driver_id) references Drivers
);

drop table if exists Orders;
create table Orders
(
    id              bigint default NEXTVAL('orders_seq') not null,
    date            timestamp                            not null,
    origin          varchar(255)                         not null,
    destination     varchar(255)                         not null,
    price           double precision                     not null,
    waiting_price   double precision,
    total_price     double precision,
    commission      double precision,
    distance        double precision                     not null,
    duration        double precision                     not null,
    status          "orderStatus"                        not null,
    start_wait_time timestamp,
    stop_wait_time  timestamp,
    end_time        timestamp,
    payment_method  "paymentMethod"                      not null,
    driver_id       bigint,
    customer_id     bigint,
    tariff_id       bigint,
    version         bigint default 0,
    primary key (id),
    foreign key (tariff_id) references Tariffs,
    foreign key (driver_id) references Drivers,
    foreign key (customer_id) references Customers
);

drop table if exists DraftOrders;
create table DraftOrders
(
    id             bigint           not null,
    date           timestamp        not null,
    origin         varchar(255)     not null,
    destination    varchar(255)     not null,
    distance       double precision not null,
    duration       double precision not null,
    prices         json             not null,
    customer_id    bigint           not null,
    payment_method "paymentMethod"  not null,
    primary key (id),
    foreign key (customer_id) references Customers
);

drop table if exists Driver_requests;
create table Driver_requests
(
    id         bigint default NEXTVAL('driver_req_seq') not null,
    date       timestamp                                not null,
    first_name varchar(255)                             not null,
    last_name  varchar(255)                             not null,
    email      varchar(255) unique                      not null,
    license    varchar(255) unique                      not null,
    status     "DriverRequestStatus"                    not null,
    primary key (id)
);


