package chess.dto;

import chess.domain.piece.Score;
import chess.domain.piece.Team;

public class ScoreStatusDto {
    private final double whiteTeamScore;
    private final double blackTeamScore;
    private final String winnerTeam;

    public ScoreStatusDto(double whiteTeamScore, double blackTeamScore, String winnerTeam) {
        this.whiteTeamScore = whiteTeamScore;
        this.blackTeamScore = blackTeamScore;
        this.winnerTeam = winnerTeam;
    }

    public static ScoreStatusDto of(Score whiteTeamScore, Score blackTeamScore, Team winnerTeam) {
        return new ScoreStatusDto(whiteTeamScore.getValue(), blackTeamScore.getValue(), winnerTeam.name());
    }

    public double getWhiteTeamScore() {
        return whiteTeamScore;
    }

    public double getBlackTeamScore() {
        return blackTeamScore;
    }

    public String getWinnerTeam() {
        return winnerTeam;
    }
}
