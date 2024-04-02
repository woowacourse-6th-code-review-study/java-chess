package chess.domain.piece;

public enum PieceMoveResult {
    SUCCESS, FAILURE, CATCH, WHITE_WIN, BLACK_WIN;

    public boolean isEnd() {
        return this.equals(BLACK_WIN) || this.equals(WHITE_WIN);
    }
}
