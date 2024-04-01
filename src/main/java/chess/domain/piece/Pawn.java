package chess.domain.piece;

import static chess.domain.Position.A2;
import static chess.domain.Position.A7;
import static chess.domain.Position.B2;
import static chess.domain.Position.B7;
import static chess.domain.Position.C2;
import static chess.domain.Position.C7;
import static chess.domain.Position.D2;
import static chess.domain.Position.D7;
import static chess.domain.Position.E2;
import static chess.domain.Position.E7;
import static chess.domain.Position.F2;
import static chess.domain.Position.F7;
import static chess.domain.Position.G2;
import static chess.domain.Position.G7;
import static chess.domain.Position.H2;
import static chess.domain.Position.H7;
import static chess.domain.piece.Team.BLACK;
import static chess.domain.piece.Team.WHITE;

import chess.domain.Position;
import chess.domain.board.ChessBoard;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class Pawn extends AbstractPiece {
    private static final Map<Team, Set<Position>> MOVE_FORWARD_TWO_ABLE_POSITIONS = Map.of(
            WHITE, Set.of(A2, B2, C2, D2, E2, F2, G2, H2),
            BLACK, Set.of(A7, B7, C7, D7, E7, F7, G7, H7)
    );

    public Pawn(Position position, Team team) {
        super(position, team);
    }

    @Override
    public PieceMoveResult tryMove(Position targetPosition, ChessBoard chessBoard) {
        if (isMoveForward(targetPosition) && isEmpty(targetPosition, chessBoard)) {
            return PieceMoveResult.SUCCESS;
        }
        if (isMoveForwardTwo(targetPosition, chessBoard) && isEmpty(targetPosition, chessBoard)) {
            return PieceMoveResult.SUCCESS;
        }
        if (isMoveDiagonal(targetPosition) && isOtherTeam(targetPosition, chessBoard)) {
            return PieceMoveResult.CATCH;
        }
        return PieceMoveResult.FAILURE;
    }

    private boolean isMoveForward(Position targetPosition) {
        Position nowPosition = getPosition();
        boolean sameColumn = nowPosition.isSameColumn(targetPosition);
        boolean rightDirection = nowPosition.rowDistance(targetPosition) == forwardDirection();
        return rightDirection && sameColumn;
    }

    private int forwardDirection() {
        Team team = getTeam();
        if (team.equals(WHITE)) {
            return 1;
        }
        return -1;
    }

    private boolean isEmpty(Position targetPosition, ChessBoard chessBoard) {
        Optional<Team> targetTeam = chessBoard.whichTeam(targetPosition);
        return targetTeam.isEmpty();
    }

    private boolean isMoveForwardTwo(Position targetPosition, ChessBoard chessBoard) {
        Position nowPosition = getPosition();
        List<Position> route = nowPosition.route(targetPosition);
        boolean rightDirection = nowPosition.rowDistance(targetPosition) == 2 * forwardDirection();
        boolean sameColumn = nowPosition.isSameColumn(targetPosition);
        boolean firstMove = isFirstMove();
        boolean allPieceOnRouteIsEmpty = isAllPieceOnRouteIsEmpty(chessBoard, route);
        return rightDirection && sameColumn && firstMove && allPieceOnRouteIsEmpty;
    }

    private boolean isAllPieceOnRouteIsEmpty(ChessBoard chessBoard, List<Position> route) {
        return route.stream()
                .map(chessBoard::whichTeam)
                .allMatch(Optional::isEmpty);
    }

    private boolean isFirstMove() {
        Team team = getTeam();
        Set<Position> positions = MOVE_FORWARD_TWO_ABLE_POSITIONS.get(team);
        return positions.contains(getPosition());
    }

    private boolean isMoveDiagonal(Position targetPosition) {
        Position nowPosition = getPosition();
        int rowDistance = nowPosition.rowDistance(targetPosition);
        int columnDistance = nowPosition.columnDistance(targetPosition);
        return rowDistance == forwardDirection() && Math.abs(columnDistance) == 1;
    }

    private boolean isOtherTeam(Position targetPosition, ChessBoard chessBoard) {
        return chessBoard.whichTeam(targetPosition)
                .filter(team -> {
                    Team otherTeam = getTeam().otherTeam();
                    return team.equals(otherTeam);
                })
                .isPresent();
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public double getPoint() {
        return 1.0;
    }
}
