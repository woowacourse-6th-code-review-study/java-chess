package dto;


import domain.piece.Color;

public record TurnDto(String turn) {
    public static TurnDto of(Color turn) {
        return new TurnDto(
                turn.name()
        );
    }

    public Color getTurn() {
        return Color.valueOf(turn);
    }
}
