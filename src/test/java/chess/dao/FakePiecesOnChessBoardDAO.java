package chess.dao;

import chess.domain.Position;
import chess.domain.piece.Piece;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FakePiecesOnChessBoardDAO implements PiecesOnChessBoardDAO {
    private final Set<Piece> pieces = new HashSet<>();

    @Override
    public boolean save(Piece piece) {
        return pieces.add(piece);
    }

    @Override
    public boolean saveAll(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (this.pieces.contains(piece)) {
                return false;
            }
        }
        for (Piece piece : pieces) {
            save(piece);
        }
        return true;
    }

    @Override
    public List<Piece> selectAll() {
        return pieces.stream().toList();
    }

    @Override
    public boolean delete(Position targetPosition) {
        return pieces.removeIf(piece -> piece.isOn(targetPosition));
    }

    @Override
    public void deleteAll() {
        pieces.clear();
    }
}
