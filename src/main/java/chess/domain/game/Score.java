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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Score score1 = (Score) o;
        return Objects.equals(score, score1.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }
}
