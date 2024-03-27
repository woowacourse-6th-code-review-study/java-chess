package model;

public record Score(float value) {

    public Score add(final Score target) {
        return new Score(value + target.value);
    }

    public Score minus(final Score target) {
        return new Score(value - target.value);
    }
}
