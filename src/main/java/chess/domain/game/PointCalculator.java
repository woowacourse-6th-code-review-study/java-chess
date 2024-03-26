package chess.domain.game;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Team;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PointCalculator {
    public static double calculatePoint(Team team, List<Piece> piecesOnBoard) {
        piecesOnBoard = Collections.unmodifiableList(piecesOnBoard);
        double totalPoint = calculateWithOutSameColumnPawn(team, piecesOnBoard);
        Map<Integer, List<Piece>> pawnsAtSameColumn = pawnGroupingByColumn(team, piecesOnBoard);
        double correctionPoint = calculatePawnCorrectionPoint(pawnsAtSameColumn);
        return totalPoint - correctionPoint;
    }

    private static double calculateWithOutSameColumnPawn(Team team, List<Piece> piecesOnBoard) {
        return piecesOnBoard.stream()
                .filter(piece -> piece.isTeamWith(team))
                .mapToDouble(Piece::getPoint)
                .reduce(0.0, Double::sum);
    }

    private static Map<Integer, List<Piece>> pawnGroupingByColumn(Team team, List<Piece> piecesOnBoard) {
        return piecesOnBoard.stream()
                .filter(piece -> piece.isTeamWith(team))
                .filter(PointCalculator::isPawn)
                .collect(Collectors.groupingBy(Piece::getColumn, Collectors.toUnmodifiableList()));
    }

    private static boolean isPawn(Piece piece) {
        PieceType pieceType = piece.getPieceType();
        return pieceType.equals(PieceType.PAWN);
    }

    private static double calculatePawnCorrectionPoint(Map<Integer, List<Piece>> pawnsAtSameColumn) {
        final double correctionPointPerPawn = 0.5;
        return pawnsAtSameColumn.values().stream()
                .filter(pieces -> pieces.size() > 1)
                .mapToDouble(pieces -> pieces.size() * correctionPointPerPawn)
                .reduce(0.0, Double::sum);
    }
}
