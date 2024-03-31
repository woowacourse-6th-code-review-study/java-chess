package chess.repository.fake;

import chess.domain.board.position.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.repository.BoardRepository;
import java.util.List;
import java.util.Map;

public class NotExistsPieceRepository implements BoardRepository {

    @Override
    public void savePiece(Piece piece, Position position, Long roomId) {
    }

    @Override
    public boolean existsPieceByPosition(Position position, Long roomId) {
        return false;
    }

    @Override
    public void deletePieceByPosition(Position position, Long roomId) {
    }

    @Override
    public Piece findPieceByPosition(Position position, Long roomId) {
        return null;
    }

    @Override
    public List<Piece> findPieceByColor(Color piece_color, Long roomId) {
        return null;
    }

    @Override
    public List<Integer> getPieceCountByPieceType(PieceType pieceType, Long roomId) {
        return null;
    }

    @Override
    public Map<Position, Piece> findAllPieceByRoomId(Long roomId) {
        return null;
    }

    @Override
    public List<Piece> findPieceByPieceType(PieceType pieceType, Long roomId) {
        return null;
    }
}
