package chess.domain.game;

import java.util.Objects;

public final class Score {
    private final Double score;

    public Score(Double score) {
        this.score = score;
    }

    public boolean isGreaterThan(Score other) {
        return score > other.score;
    }

    public boolean isLowerThan(Score other) {
        return score < other.score;
    }

    public Double score() {
        return score;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (Score) obj;
        return Objects.equals(this.score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }
}
