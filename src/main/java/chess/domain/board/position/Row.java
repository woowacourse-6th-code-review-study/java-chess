package chess.domain.board.position;

import java.util.Arrays;

public enum Row {
    EIGHT(8),
    SEVEN(7),
    SIX(6),
    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2),
    ONE(1);

    private final int index;

    Row(int index) {
        this.index = index;
    }

    public Row calculateNextRow(int distance) {
        return Arrays.stream(values())
                .filter(row -> row.index == this.index + distance)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("다음 위치로 이동할 수 있는 열이 없습니다."));
    }

    public static Row findByIndex(int index) {
        return Arrays.stream(values())
                .filter(row -> row.index == index)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("열과 일치하는 인덱스가 없습니다."));
    }

    public boolean isNextInRange(int distance) {
        int nextIndex = index + distance;
        return EIGHT.index >= nextIndex && nextIndex >= ONE.index;
    }

    public int getIndex() {
        return index;
    }
}
