package model;

import java.util.Arrays;
import java.util.Objects;
import model.piece.Bishop;
import model.piece.BlackPawn;
import model.piece.King;
import model.piece.Knight;
import model.piece.Piece;
import model.piece.Queen;
import model.piece.Rook;
import model.piece.WhitePawn;

public enum PieceScore {

    KING(King.class, new Score(0)),
    QUEEN(Queen.class, new Score(9)),
    ROOK(Rook.class, new Score(5)),
    BISHOP(Bishop.class, new Score(3)),
    KNIGHT(Knight.class, new Score(2.5F)),
    WHITE_PAWN(WhitePawn.class, new Score(1)), //TODO whitePawn, blackPawn 나누지 않는 방법고민
    BLACK_PAWN(BlackPawn.class, new Score(1));

    private final Class<? extends Piece> clazz;
    private final Score score;

    PieceScore(final Class<? extends Piece> clazz, final Score score) {
        this.clazz = clazz;
        this.score = score;
    }

    public static Score getScore(final Piece piece) {
        return Arrays.stream(values())
                .filter(pieceScore -> Objects.equals(pieceScore.clazz, piece.getClass()))
                .map(pieceScore -> pieceScore.score)
                .findFirst()
                .orElseThrow();
    }
}
