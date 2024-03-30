package chess.dao;

import chess.domain.Position;
import chess.domain.piece.Piece;
import java.sql.Connection;
import java.util.List;

public interface PiecesOnChessBoardDAO {
    boolean save(Piece piece, Connection connection);

    boolean saveAll(List<Piece> pieces, Connection connection);

    List<Piece> selectAll(Connection connection);

    boolean delete(Position targetPosition, Connection connection);

    void deleteAll(Connection connection);

}
