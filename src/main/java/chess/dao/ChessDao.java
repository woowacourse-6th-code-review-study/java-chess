package chess.dao;

import chess.domain.Team;
import chess.domain.position.Position;
import java.util.List;

public class ChessDao {

    private final ChessGameRepository chessGameRepository;
    private final PieceRepository pieceRepository;

    public ChessDao(ChessGameRepository chessGameRepository, PieceRepository pieceRepository) {
        this.chessGameRepository = chessGameRepository;
        this.pieceRepository = pieceRepository;
    }

    public boolean isExistSavingGame() {
        return chessGameRepository.isExistGame();
    }

    public List<PieceEntity> findAllPieces() {
        return pieceRepository.findAll();
    }

    public void saveBoard(List<PieceEntity> pieces, Team currentTurn) {
        pieceRepository.saveAll(pieces);
        chessGameRepository.update(currentTurn);
    }

    public void saveMoving(PieceEntity piece, Position previous, Team currentTurn) {
        PieceEntity emptyPiece = PieceEntity.createEmptyPiece(previous);

        pieceRepository.update(piece);
        pieceRepository.update(emptyPiece);
        chessGameRepository.update(currentTurn);
    }

    public void deleteAll() {
        pieceRepository.deleteAll();
        chessGameRepository.deleteAll();
    }
}
