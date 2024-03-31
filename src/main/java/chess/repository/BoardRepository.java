package chess.repository;

import chess.domain.board.position.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.List;
import java.util.Map;

public interface BoardRepository {

    void savePiece(Piece piece, Position position, Long roomId);

    boolean existsPieceByPosition(Position position, Long roomId);

    void deletePieceByPosition(Position position, Long roomId);

    Piece findPieceByPosition(Position position, Long roomId);

    List<Piece> findPiecesByColor(Color piece_color, Long roomId);

    List<Integer> getPieceCountByPieceType(PieceType pieceType, Long roomId);

    Map<Position, Piece> findAllPieceByRoomId(Long roomId);

    List<Piece> findPieceByPieceType(PieceType pieceType, Long roomId);
}
