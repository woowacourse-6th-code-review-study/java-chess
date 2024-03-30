package chess;

import chess.domain.piece.Team;

public enum GameStatus {

    PLAYING,
    END,
    BLACK_WIN,
    WHITE_WIN;

    public static GameStatus whenWin(Team team) {
        if (team.isBlack()) {
            return BLACK_WIN;
        }
        return WHITE_WIN;
    }

    public static boolean isPlaying(GameStatus gameStatus) {
        return gameStatus == GameStatus.PLAYING;
    }
}
