package domain.piece;

import java.util.Arrays;

public enum PieceColor {
    BLACK, WHITE;

    public static PieceColor of(final String value) {
        return Arrays.stream(PieceColor.values())
                .filter(pieceColor -> pieceColor.name().equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 PieceColor 입니다."));
    }

    public PieceColor toggle() {
        if (this == BLACK) {
            return WHITE;
        }

        return BLACK;
    }
}
