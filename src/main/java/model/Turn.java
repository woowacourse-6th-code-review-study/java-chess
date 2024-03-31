package model;

public record Turn(int count) {
    public Turn take() {
        return new Turn(count + 1);
    }
}
