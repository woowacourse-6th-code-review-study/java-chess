package chess.domain.position;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.BiPredicate;

public enum BoardDirection {
    N((start, destination) -> start.isOrthogonalWith(destination) && start.isBelow(destination), -1, 0),
    S((start, destination) -> start.isOrthogonalWith(destination) && start.isAbove(destination), 1, 0),
    E((start, destination) -> start.isOrthogonalWith(destination) && start.isFurtherLeftThan(destination), 0, 1),
    W((start, destination) -> start.isOrthogonalWith(destination) && start.isFurtherRightThan(destination), 0, -1),
    NE((start, destination) -> start.isDiagonalWith(destination) && start.isBelow(destination)
            && start.isFurtherLeftThan(destination), -1, 1),
    NW((start, destination) -> start.isDiagonalWith(destination) && start.isBelow(destination)
            && start.isFurtherRightThan(destination), -1, -1),
    SW((start, destination) -> start.isDiagonalWith(destination) && start.isAbove(destination)
            && start.isFurtherRightThan(destination), 1, -1),
    SE((start, destination) -> start.isDiagonalWith(destination) && start.isAbove(destination)
            && start.isFurtherLeftThan(destination), 1, 1),
    ;

    private final BiPredicate<FileRankPosition, FileRankPosition> matchCondition;
    private final int moveOnceFileWeight;
    private final int moveOnceRankWeight;

    BoardDirection(BiPredicate<FileRankPosition, FileRankPosition> matchCondition, int moveOnceFileWeight,
                   int moveOnceRankWeight) {
        this.matchCondition = matchCondition;
        this.moveOnceFileWeight = moveOnceFileWeight;
        this.moveOnceRankWeight = moveOnceRankWeight;
    }

    public static BoardDirection of(FileRankPosition start, FileRankPosition destination) {
        return Arrays.stream(values())
                .filter(boardDirection -> boardDirection.matchCondition.test(start, destination))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("움직이는 방향을 찾는 것에 실패하였습니다"));
    }
}
