package chess.dao;

import chess.domain.Position;
import chess.domain.piece.Bishop;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.piece.Team;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FakePiecesOnChessBoardDAO implements PiecesOnChessBoardDAO {
    private final Set<Piece> pieces = new HashSet<>();

    @Override
    public boolean save(Piece piece, Connection connection) {
        return pieces.add(copy(piece));
    }

    private Piece copy(Piece piece) {
        int row = piece.getRow();
        int column = piece.getColumn();
        Position position = Position.getInstance(row, column);
        Team team = piece.getTeam();
        return switch (piece.getPieceType()) {
            case ROOK -> new Rook(position, team);
            case BISHOP -> new Bishop(position, team);
            case KING -> new King(position, team);
            case PAWN -> new Pawn(position, team);
            case KNIGHT -> new Knight(position, team);
            case QUEEN -> new Queen(position, team);
        };
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
