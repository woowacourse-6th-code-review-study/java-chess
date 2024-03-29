package domain.board;

import domain.piece.Color;

import java.util.function.Function;

public enum State {
    NOT_STARTED(color -> false),
    WHITE_TURN(color -> Color.WHITE == color),
    BLACK_TURN(color -> Color.BLACK == color),
    KING_IS_DEAD(color -> false),
    ENDED(color -> false);

    private final Function<Color, Boolean> isSameTurn;

    State(final Function<Color, Boolean> isSameTurn) {
        this.isSameTurn = isSameTurn;
    }

    boolean isTurnOf(final Color color) {
        return isSameTurn.apply(color);
    }
}
