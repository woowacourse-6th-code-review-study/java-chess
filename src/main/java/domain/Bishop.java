package domain;

import static domain.PieceMoveResult.*;

import java.util.Optional;

public class Bishop extends AbstractMoveStraightMovePiece {
	public Bishop(Position position, Team team) {
		super(position, team);
	}

	@Override
	public Optional<PieceMoveResult> tryMoveAssumeAlone(Position targetPosition,
		PiecesOnChessBoard piecesOnChessBoard) {
		Position nowPosition = getPosition();
		int absRowDistance = Math.abs(nowPosition.rowDistance(targetPosition));
		int absColDistance = Math.abs(nowPosition.columnDistance(targetPosition));
		if (absColDistance != absRowDistance) {
			return Optional.of(FAILURE);
		}
		return Optional.empty();
	}
}