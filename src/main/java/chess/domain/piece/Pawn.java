package chess.domain.piece;

import chess.domain.position.Position;

public class Pawn implements Piece {
    @Override
    public boolean canMoveTo(Position target) {
        //TODO: 폰 움직임 전략 구현 필요
        return false;
    }
}