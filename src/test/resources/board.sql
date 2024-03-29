use chess_test;

# drop table board;

create table board
(
    position   varchar(2)  not null,
    piece_type varchar(6) not null,
    camp       varchar(5)  not null

);
