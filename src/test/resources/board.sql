use chess_test;

drop table if exists board;

create table board
(
    position   varchar(2)  not null,
    piece_type varchar(6) not null,
    camp       varchar(5)  not null

);
