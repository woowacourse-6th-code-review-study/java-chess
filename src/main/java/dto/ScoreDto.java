package dto;

import model.Camp;
import model.ChessGame;
import model.Score;

public class ScoreDto {

    private final float blackScore;
    private final float whiteScore;

    private ScoreDto(final float blackScore, final float whiteScore) {
        this.blackScore = blackScore;
        this.whiteScore = whiteScore;
    }

    public static ScoreDto from(final ChessGame chessGame) {
        final Score black = chessGame.calculateScore(Camp.BLACK);
        final Score white = chessGame.calculateScore(Camp.WHITE);
        return new ScoreDto(black.value(), white.value());
    }

    public float getBlackScore() {
        return blackScore;
    }

    public float getWhiteScore() {
        return whiteScore;
    }
}
