package chess.domain.piece;

import chess.domain.board.position.Direction;
import chess.domain.board.position.Position;
import chess.domain.strategy.BishopMoveStrategy;
import chess.domain.strategy.BlackPawnMoveStrategy;
import chess.domain.strategy.KingMoveStrategy;
import chess.domain.strategy.KnightMoveStrategy;
import chess.domain.strategy.MoveStrategy;
import chess.domain.strategy.QueenMoveStrategy;
import chess.domain.strategy.RookMoveStrategy;
import chess.domain.strategy.WhitePawnMoveStrategy;
import java.util.Map;
import java.util.Queue;

public enum PieceType {
    BLACK_PAWN(new BlackPawnMoveStrategy(), 1),
    WHITE_PAWN(new WhitePawnMoveStrategy(), 1),
    ROOK(new RookMoveStrategy(), 5),
    KNIGHT(new KnightMoveStrategy(), 2.5),
    BISHOP(new BishopMoveStrategy(), 3),
    QUEEN(new QueenMoveStrategy(), 9),
    KING(new KingMoveStrategy(), 0);

    private final MoveStrategy moveStrategy;
    private final double score;

    PieceType(MoveStrategy moveStrategy, double score) {
        this.moveStrategy = moveStrategy;
        this.score = score;
    }

    public Map<Direction, Queue<Position>> generateAllDirectionPositions(Position currentPosition) {
        return this.moveStrategy.generateMovablePositions(currentPosition);
    }

    public double getScore() {
        return score;
    }

    public boolean isBlackPawn() {
        return this == BLACK_PAWN;
    }

    public boolean isWhitePawn() {
        return this == WHITE_PAWN;
    }
}
