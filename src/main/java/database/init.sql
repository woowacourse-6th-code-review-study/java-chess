create table current_player_color
(
    id bigint auto_increment primary key,
    player_color varchar(10) not null
);

create table piece
(
    type varchar(10) not null,
    `rank` varchar(10) not null,
    file varchar(10) not null,
    color varchar(10) not null,
    id int auto_increment primary key
);

