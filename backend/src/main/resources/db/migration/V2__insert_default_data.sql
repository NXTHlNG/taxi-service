delete
from Cars;
insert into Cars(name, color, year, plate)
values ('Toyota Camry', 'серый', 2016, 'А862СМ 164 RUS'),
       ('Skoda Octavia', 'черный', 2018, 'A452ММ 164 RUS'),
       ('Lada Priora', 'серый', 2014, 'А777АА 777 RUS'),
       ('Hyundai Solaris', 'желтый', 2020, 'А862СМ 164 RUS'),
       ('Mercedes E', 'белый', 2017, 'П178ПМ 164 RUS');

delete
from Tariffs;
insert into Tariffs(name, rank, min_price, minute_price, kilometer_price, waiting_price, free_waiting, commission)
values ('Эконом', 1, 59, 6, 15, 6, 3, 18),
       ('Комфорт', 2, 89, 6, 20, 6, 3, 19),
       ('Бизнес', 3, 200, 10, 30, 10, 3, 21);

delete
from Roles;
insert into Roles(name)
values ('ROLE_ADMIN'),
       ('ROLE_DRIVER'),
       ('ROLE_CUSTOMER');
