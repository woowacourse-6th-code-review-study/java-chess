package chess.domain.mock;

import chess.domain.board.position.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.repository.BoardRepository;
import java.util.List;
import java.util.Map;

public class BlackPieceRepository implements BoardRepository {

    @Override
    public void savePiece(Piece piece, Position position) {
    }

    @Override
    public boolean existsPieceByPosition(Position position) {
        return true;
    }

    @Override
    public void deletePieceByPosition(Position position) {
    }

    @Override
    public Piece findPieceByPosition(Position position) {
        return new Piece(PieceType.BLACK_PAWN, Color.BLACK);
    }

    @Override
    public List<Piece> findPieceByColor(Color color) {
        return null;
    }

    @Override
    public List<Integer> getPieceCountByPieceType(PieceType pieceType) {
        return null;
    }

    @Override
    public Map<Position, Piece> findAllPiece() {
        return null;
    }

    @Override
    public List<Piece> findPieceByPieceType(PieceType pieceType) {
        return null;
    }
}
