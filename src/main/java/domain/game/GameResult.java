package domain.game;

public enum GameResult {
    BLACK_WIN, WHITE_WIN, DRAW;

    public static GameResult of(final Score whiteScore, final Score blackScore) {
        if (blackScore.isBigger(whiteScore)) {
            return BLACK_WIN;
        }

        if (whiteScore.isBigger(blackScore)) {
            return WHITE_WIN;
        }

        return DRAW;
    }
}
