create table pieces
(
    board_file varchar(1) not null,
    board_rank varchar(1) not null,
    color      varchar(8) not null,
    type       varchar(8) not null,
    primary key (board_file, board_rank)
);

create table turns
(
    turn varchar(8) not null,
    primary key (turn)
)
