package chess.dao;

import chess.domain.Position;
import chess.domain.piece.Piece;
import java.util.List;

public interface PiecesOnChessBoardDAO {
    boolean save(Piece piece);

    boolean saveAll(List<Piece> pieces);

    List<Piece> selectAll();

    boolean delete(Position targetPosition);

    boolean deleteAll();

}
