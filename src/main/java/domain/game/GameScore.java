package domain.game;

public record GameScore(Score whiteTeamScore, Score blackTeamScore) {

    public GameResult gameResult() {
        return GameResult.of(whiteTeamScore, blackTeamScore);
    }
}
