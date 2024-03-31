package chess.repository;

import chess.domain.board.position.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.List;
import java.util.Map;

public interface BoardRepository {

    void savePiece(Piece piece, Position position);

    boolean existsPieceByPosition(Position position);

    void deletePieceByPosition(Position position);

    Piece findPieceByPosition(Position position);

    List<Piece> findPieceByColor(Color color);

    List<Integer> getPieceCountByPieceType(PieceType pieceType);

    Map<Position, Piece> findAllPiece();

    List<Piece> findPieceByPieceType(PieceType pieceType);
}
