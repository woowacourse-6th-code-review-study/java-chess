package dto;


import domain.piece.Color;

public record StateDto(String state, int gameId) {
    public Color getState() {
        return Color.valueOf(state);
    }
}
