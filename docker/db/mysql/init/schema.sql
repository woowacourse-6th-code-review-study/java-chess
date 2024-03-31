create table rooms
(
    room_id int,
    primary key (room_id)
);

create table game_states
(
    game_id int,
    state   varchar(8) not null,
    primary key (game_id),
    foreign key (game_id) references rooms (room_id)
);

create table pieces
(
    board_file varchar(1) not null,
    board_rank varchar(1) not null,
    color      varchar(8) not null,
    type       varchar(8) not null,
    game_id    int,
    primary key (board_file, board_rank),
    foreign key (game_id) references rooms (room_id)
);
