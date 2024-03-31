use chess_test;

drop table if exists turn;

create table turn
(
    camp varchar(5) not null,
    count int not null
);
