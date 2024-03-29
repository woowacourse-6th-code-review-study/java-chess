package chess.domain.board;

import chess.domain.board.position.Column;
import chess.domain.board.position.Position;
import chess.domain.game.Score;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public Map<Color, Score> calculateScore() {
        return Arrays.stream(Color.values()).collect(Collectors.toMap(
                color -> color,
                this::calculateTotalScore
        ));
    }

    private Score calculateTotalScore(Color color) {
        double sum = sumTotalScore(color);
        double pawnMinus = calculatePawnScore(color);
        return new Score(sum - pawnMinus);
    }

    private double sumTotalScore(Color color) {
        return board.values().stream()
                .filter(piece -> piece.isSameColor(color))
                .mapToDouble(Piece::getScore)
                .sum();
    }

    private Map<Column, Long> countPawnByColumn(Color color) {
        return board.keySet().stream()
                .filter(position -> board.get(position).isPawnByColor(color))
                .collect(Collectors.groupingBy(Position::getColumn, Collectors.counting()));
    }

    private double calculatePawnScore(Color color) {
        Map<Column, Long> pawnCountByColumn = countPawnByColumn(color);
        return pawnCountByColumn.values().stream()
                .filter(pawnCount -> pawnCount >= 2)
                .mapToDouble(pawnCount -> pawnCount * 0.5)
                .sum();
    }

    public void movePiece(Position from, Position to) {
        Piece fromPiece = board.get(from);
        board.put(to, fromPiece);
        board.remove(from);
    }

    public Piece findPieceByPosition(Position position) {
        if (existPiece(position)) {
            return board.get(position);
        }
        throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
    }

    public boolean isEmptySpace(Position position) {
        return !existPiece(position);
    }

    public boolean existPiece(Position position) {
        return board.containsKey(position);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    void put(Position position, Piece piece) {
        board.put(position, piece);
    }
}
