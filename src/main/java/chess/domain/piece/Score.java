package chess.domain.piece;

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
}
