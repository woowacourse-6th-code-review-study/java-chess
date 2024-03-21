package domain.piece;

import domain.position.Position;

public class Knight extends AbstractPiece {
    public Knight(Color color) {
        super(color);
    }

    @Override
    public void validateMovement(Position resource, Position target, Piece other) {
        if (this.getColor() == other.getColor()) {
            throw new IllegalArgumentException("같은 팀의 말을 잡을 수 없습니다.");
        }
        int rankGap = resource.calculateRankGap(target);
        int fileGap = resource.calculateFileGap(target);
        if (rankGap * fileGap == 2) {
            return;
        }
        throw new IllegalArgumentException(String.format("%s은 L자 방향으로만 이동할 수 있습니다.",
                this.getClass().getSimpleName()));
    }
}