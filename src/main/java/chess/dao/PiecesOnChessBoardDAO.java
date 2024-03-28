package chess.dao;

import chess.domain.Position;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Team;
import java.util.List;

public interface PiecesOnChessBoardDAO {
    List<Piece> selectAll();

    boolean delete(Position targetPosition);

    boolean save(Piece piece);

    boolean update(Position targetPosition, PieceType pieceType, Team team);
}
