package chess.domain.board;

import chess.domain.board.position.Column;
import chess.domain.board.position.Position;
import chess.domain.board.position.Row;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public Map<Position, Piece> initialize() {
        Map<Position, Piece> map = new HashMap<>();
        initializeBlackTeam(map);
        initializeWhiteTeam(map);
        return map;
    }

    private void initializeBlackTeam(Map<Position, Piece> map) {
        initializePawn(map, Row.SEVEN, Color.BLACK);
        initializeHighValuePiece(map, Row.EIGHT, Color.BLACK);
    }

    private void initializeWhiteTeam(Map<Position, Piece> map) {
        initializePawn(map, Row.TWO, Color.WHITE);
        initializeHighValuePiece(map, Row.ONE, Color.WHITE);
    }

    private void initializePawn(Map<Position, Piece> map, Row row, Color color) {
        for (Column column : Column.values()) {
            Position position = new Position(row, column);
            if (color == Color.WHITE) {
                map.put(position, new Piece(PieceType.WHITE_PAWN, color));
            }
            if (color == Color.BLACK) {
                map.put(position, new Piece(PieceType.BLACK_PAWN, color));
            }
        }
    }

    private void initializeHighValuePiece(Map<Position, Piece> map, Row row, Color color) {
        map.put(new Position(row, Column.A), new Piece(PieceType.ROOK, color));
        map.put(new Position(row, Column.H), new Piece(PieceType.ROOK, color));

        map.put(new Position(row, Column.B), new Piece(PieceType.KNIGHT, color));
        map.put(new Position(row, Column.G), new Piece(PieceType.KNIGHT, color));

        map.put(new Position(row, Column.C), new Piece(PieceType.BISHOP, color));
        map.put(new Position(row, Column.F), new Piece(PieceType.BISHOP, color));

        map.put(new Position(row, Column.D), new Piece(PieceType.QUEEN, color));
        map.put(new Position(row, Column.E), new Piece(PieceType.KING, color));
    }
}
