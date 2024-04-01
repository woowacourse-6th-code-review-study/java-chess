package chess.dao;

import chess.domain.Position;
import chess.domain.piece.Piece;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FakePiecesOnChessBoardDAO implements PiecesOnChessBoardDAO {
    private final Set<Piece> pieces = new HashSet<>();

    @Override
    public boolean save(Piece piece, Connection connection) {
        return pieces.add(piece);
    }

    @Override
    public boolean saveAll(List<Piece> pieces, Connection connection) {
        for (Piece piece : pieces) {
            if (this.pieces.contains(piece)) {
                return false;
            }
        }
        for (Piece piece : pieces) {
            save(piece, connection);
        }
        return true;
    }

    @Override
    public List<Piece> selectAll(Connection connection) {
        return pieces.stream().toList();
    }

    @Override
    public boolean isNotEmpty(Connection connection) {
        return !selectAll(connection).isEmpty();
    }

    @Override
    public boolean delete(Position targetPosition, Connection connection) {
        return pieces.removeIf(piece -> piece.isOn(targetPosition));
    }

    @Override
    public void deleteAll(Connection connection) {
        pieces.clear();
    }
}
