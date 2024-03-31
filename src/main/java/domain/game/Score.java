package domain.game;

import domain.board.Board;
import domain.board.File;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

import java.util.List;
import java.util.Map;

public record Score(double value) {

    public static Score of(final Board board, final PieceColor pieceColor) {
        Map<Position, Piece> piecePositions = board.piecePositions();
        double existTargetColorPiecesScoreSum = sumExistTargetPiecesScore(piecePositions, pieceColor);
        double scoreValue = existTargetColorPiecesScoreSum - calculateDecreaseScoreForExistSameFilePawns(piecePositions, pieceColor);

        return new Score(scoreValue);
    }

    private static double sumExistTargetPiecesScore(final Map<Position, Piece> piecePositions, final PieceColor pieceColor) {
        return piecePositions.values()
                .stream()
                .filter(piece -> piece.isTeam(pieceColor))
                .mapToDouble(Piece::score)
                .sum();
    }

    private static double calculateDecreaseScoreForExistSameFilePawns(final Map<Position, Piece> piecePositions, final PieceColor pieceColor) {
        List<Position> targetColorPawnPositions = piecePositions.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isTeam(pieceColor) && entry.getValue().matchPieceType(PieceType.PAWN))
                .map(Map.Entry::getKey)
                .toList();

        int sameFilePawnCount = 0;
        for (File file : File.values()) {
            sameFilePawnCount += calculateSameFilePawnCount(targetColorPawnPositions, file);
        }

        return 0.5 * sameFilePawnCount;
    }

    private static int calculateSameFilePawnCount(final List<Position> pawnPositions, final File file) {
        int sameFilePawnCount = (int) pawnPositions.stream()
                .filter(position -> position.file() == file)
                .count();

        if (sameFilePawnCount <= 1) {
            return 0;
        }

        return sameFilePawnCount;
    }

    public boolean isBigger(final Score other) {
        return this.value > other.value;
    }
}
