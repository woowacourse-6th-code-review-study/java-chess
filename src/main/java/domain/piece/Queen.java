package domain.piece;

import domain.position.Position;

public class Queen extends AbstractColorValidatablePiece {
    public Queen(Color color) {
        super(color);
    }

    @Override
    public void validatePieceMovement(final Position resource, final Position target) {
        int rankGap = resource.calculateRankGap(target);
        int fileGap = resource.calculateFileGap(target);
        if (rankGap == 0 && fileGap == 0) {
            throw new IllegalArgumentException(String.format("%s은 대각선, 수평, 수직 방향으로만 이동할 수 있습니다.",
                    this.getClass().getSimpleName()));
        }
        if ((rankGap == 0 && fileGap > 0) || (rankGap > 0 && fileGap == 0)) {
            return;
        }
        if (rankGap == fileGap) {
            return;
        }
        throw new IllegalArgumentException(String.format("%s은 대각선, 수평, 수직 방향으로만 이동할 수 있습니다.",
                this.getClass().getSimpleName()));
    }

    @Override
    public Type getType() {
        return Type.QUEEN;
    }
}
