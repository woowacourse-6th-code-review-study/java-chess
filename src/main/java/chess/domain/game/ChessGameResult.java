package chess.domain.game;

import chess.domain.piece.Color;
import java.util.Map;

public class ChessGameResult {

    private final Winner winner;
    private final Map<Color, Score> teamScore;

    public ChessGameResult(Winner winner, Map<Color, Score> teamScore) {
        this.winner = winner;
        this.teamScore = teamScore;
    }

    public Winner getWinner() {
        return winner;
    }

    public Map<Color, Score> getTeamScore() {
        return teamScore;
    }
}
