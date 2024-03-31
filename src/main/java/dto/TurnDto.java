package dto;


import domain.piece.Color;

public record TurnDto(String turn, int gameId) {
    public Color getTurn() {
        return Color.valueOf(turn);
    }
}
