package dto;


import domain.piece.Color;

public record TurnDto(String turn, String gameId) {
    public static TurnDto of(Color turn) {
        return new TurnDto(
                turn.name(),
                null
        );
    }

    public Color getTurn() {
        return Color.valueOf(turn);
    }
}
