package domain.board;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> piecePositions;

    public Board(final Map<Position, Piece> piecePositions) {
        this.piecePositions = piecePositions;
    }

    public void movePiece(final PieceColor pieceColor, final Position source, final Position destination) {
        validatePosition(pieceColor, source, destination);

        Piece targetPiece = piecePositions.get(source);
        targetPiece.move(source, destination, this);

        piecePositions.put(destination, targetPiece);
        piecePositions.remove(source);
    }

    private void validatePosition(final PieceColor pieceColor, final Position source, final Position destination) {
        if (source.equals(destination)) {
            throw new IllegalArgumentException("출발지와 목적지가 같을 수 없습니다.");
        }

        if (!piecePositions.containsKey(source)) {
            throw new IllegalArgumentException("출발지에 기물이 존재하지 않습니다.");
        }

        if (!piecePositions.get(source).isTeam(pieceColor)) {
            throw new IllegalArgumentException("상대방의 기물을 이동시킬 수 없습니다.");
        }
    }

    public boolean existPiece(final Position position) {
        return piecePositions.containsKey(position);
    }

    public boolean existTeamColor(final Position position, final PieceColor teamColor) {
        return piecePositions.get(position).isTeam(teamColor);

    }

    public Map<Position, Piece> piecePositions() {
        return Collections.unmodifiableMap(piecePositions);
    }

    public boolean isKingAlive(final PieceColor targetColor) {
        return piecePositions.values()
                .stream()
                .anyMatch(piece -> piece.isTeam(targetColor) && piece.pieceType() == PieceType.KING);
    }

    public double calculateTeamScore(final PieceColor teamColor) {
        double existTargetColorPiecesScoreSum = sumExistTargetPiecesScore(teamColor);

        return existTargetColorPiecesScoreSum - calculateDecreaseScoreForExistSameFilePawns(teamColor);
    }

    private double sumExistTargetPiecesScore(final PieceColor pieceColor) {
        return piecePositions.values()
                .stream()
                .filter(piece -> piece.isTeam(pieceColor))
                .mapToDouble(Piece::score)
                .sum();
    }

    private double calculateDecreaseScoreForExistSameFilePawns(final PieceColor pieceColor) {
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

    private int calculateSameFilePawnCount(final List<Position> pawnPositions, final File file) {
        int sameFilePawnCount = (int) pawnPositions.stream()
                .filter(position -> position.file() == file)
                .count();

        if (sameFilePawnCount <= 1) {
            return 0;
        }

        return sameFilePawnCount;
    }
}
