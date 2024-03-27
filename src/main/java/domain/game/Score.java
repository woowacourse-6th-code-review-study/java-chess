package domain.game;

public record Score(double value) {

    public boolean isBigger(final Score other) {
        return this.value > other.value;
    }
}
