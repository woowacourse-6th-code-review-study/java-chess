package chess.domain.position;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Rank {
    EIGHT(0, 8),
    SEVEN(1, 7),
    SIX(2, 6),
    FIVE(3, 5),
    FOUR(4, 4),
    THREE(5, 3),
    TWO(6, 2),
    ONE(7, 1);


    private final int rowNumber;
    private final int rankNumber;

    Rank(int rowNumber, int rankNumber) {
        this.rowNumber = rowNumber;
        this.rankNumber = rankNumber;
    }

    public static Rank from(int rowNumber) {
        return Arrays.stream(values())
                .filter(rank -> rank.rowNumber == rowNumber)
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(String.format("행 번호가 %d인 랭크를 찾을 수 없습니다", rowNumber)));
    }

    public Rank reverse() {
        return Rank.from(ONE.rowNumber - rowNumber);
    }

    public int calculateDistanceWith(Rank other) {
        return Math.abs(rowNumber - other.rowNumber);
    }

    public boolean isAbove(Rank other) {
        return rowNumber < other.rowNumber;
    }

    public boolean isBelow(Rank other) {
        return rowNumber > other.rowNumber;
    }

    public Rank move(int weight) {
        return from(rowNumber + weight);
    }

    public int getRankNumber() {
        return rankNumber;
    }
}
