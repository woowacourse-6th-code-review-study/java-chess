create table users
(
    username varchar(16),
    primary key (username)
);

create table rooms
(
    user varchar(16),
    room_id int,
    primary key (room_id),
    foreign key (user) references users (username)
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
    game_id    int,
    board_file varchar(1) not null,
    board_rank varchar(1) not null,
    color      varchar(8) not null,
    type       varchar(8) not null,
    primary key (game_id, board_file, board_rank),
    foreign key (game_id) references rooms (room_id)
);
