CREATE DATABASE IF NOT EXISTS chess;

USE chess;

CREATE TABLE chess_game (
    turn VARCHAR(10) CHECK (turn IN ('BLACK', 'WHITE')) not null,
    PRIMARY KEY (turn)
);

CREATE TABLE piece (
    board_file VARCHAR(2) CHECK (board_file IN ('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H')) not null,
    board_rank VARCHAR(2) CHECK (board_rank IN ('1', '2', '3', '4', '5', '6', '7', '8')) not null,
    type VARCHAR(10) CHECK (type IN ('KING', 'QUEEN', 'ROOK', 'BISHOP', 'KNIGHT', 'PAWN', 'EMPTY')) not null,
    team VARCHAR(10) CHECK (team IN ('BLACK', 'WHITE', 'EMPTY')) not null,
    PRIMARY KEY (board_rank, board_file)
);
