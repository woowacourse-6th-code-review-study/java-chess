package chess.domain.piece;

public enum PieceMoveResult {
    //Todo 게임 종료 여부 분리
    SUCCESS, FAILURE, CATCH, WHITE_WIN, BLACK_WIN;
    
    public boolean isEnd() {
        return this.equals(BLACK_WIN) || this.equals(WHITE_WIN);
    }
}
