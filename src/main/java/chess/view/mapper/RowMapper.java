package chess.view.mapper;

import chess.domain.board.position.Row;
import java.util.Arrays;

public enum RowMapper {
    RANK1(Row.ONE, "1", 7),
    RANK2(Row.TWO, "2", 6),
    RANK3(Row.THREE, "3", 5),
    RANK4(Row.FOUR, "4", 4),
    RANK5(Row.FIVE, "5", 3),
    RANK6(Row.SIX, "6", 2),
    RANK7(Row.SEVEN, "7", 1),
    RANK8(Row.EIGHT, "8", 0);

    private final Row row;
    private final String value;
    private final int arrayIndex;

    RowMapper(Row row, String value, int arrayIndex) {
        this.row = row;
        this.value = value;
        this.arrayIndex = arrayIndex;
    }

    public static Row findByInputValue(String value) {
        return Arrays.stream(values())
                .filter(row -> row.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("입력과 일치하는 열이 존재하지 않습니다."))
                .row;
    }

    public static String findByRow(Row row) {
        return Arrays.stream(values())
                .filter(rowMapper -> rowMapper.row == row)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("열과 일치하는 문자열이 존재하지 않습니다."))
                .value;
    }

    public static int findByIndex(Row row) {
        return Arrays.stream(values())
                .filter(rowMapper -> rowMapper.row == row)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("열과 일치하는 인덱스가 존재하지 않습니다."))
                .arrayIndex;
    }
}
