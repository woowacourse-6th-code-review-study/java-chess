create table current_player_color
(
    id bigint auto_increment primary key,
    player_color varchar(50) not null
);

create table piece
(
    type varchar(50) not null,
    `rank` varchar(50) not null,
    file varchar(50) not null,
    color varchar(50) not null,
    id int auto_increment primary key
);

