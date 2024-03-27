package chess.domain.piece;

import java.util.Objects;

public class Score {
    private final double value;

    public Score(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public Score add(Score other) {
        return new Score(this.value + other.value);
    }

    public Score multiply(Score other) {
        return new Score(this.value * other.value);
    }

    public boolean isAbove(Score other) {
        return this.value > other.value;
    }

    public boolean isBelow(Score other) {
        return this.value < other.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Score score = (Score) o;
        return Double.compare(value, score.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
