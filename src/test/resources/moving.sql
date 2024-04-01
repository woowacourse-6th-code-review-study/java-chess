use chess_test;

create table moving
(
    movement_id INT primary key auto_increment,
    camp        varchar(5)  not null,
    start       varchar(2) not null,
    destination varchar(2) not null
);
