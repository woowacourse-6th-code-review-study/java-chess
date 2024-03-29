use chess_test;

create table moving
(
    movement_id      INT primary key auto_increment,
    camp             varchar(5)  not null,
    start_rank       varchar(12) not null,
    start_file       varchar(12) not null,
    destination_rank varchar(12) not null,
    destination_file varchar(12) not null
);
