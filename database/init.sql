CREATE DATABASE IF NOT EXISTS chess;

USE chess;

CREATE TABLE chess_game (
    turn VARCHAR(10) CHECK (turn IN ('BLACK', 'WHITE')),
    PRIMARY KEY (turn)
);

CREATE TABLE piece (
    board_rank VARCHAR(2) CHECK (board_rank IN ('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H')),
    board_file VARCHAR(2) CHECK (board_file IN ('1', '2', '3', '4', '5', '6', '7', '8')),
    type VARCHAR(10) CHECK (type IN ('KING', 'QUEEN', 'ROOK', 'BISHOP', 'KNIGHT', 'PAWN', 'EMPTY')),
    team VARCHAR(10) CHECK (team IN ('BLACK', 'WHITE')),
    PRIMARY KEY (board_rank, board_file)
);
