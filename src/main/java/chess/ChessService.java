package chess;

import chess.dao.ChessDao;
import chess.dao.PieceEntity;
import chess.domain.Board;
import chess.domain.BoardFactory;
import chess.domain.Team;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.dto.TurnType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChessService {

    private Board board;
    private final ChessDao chessDao;

    public ChessService(ChessDao chessDao) {
        this.chessDao = chessDao;
    }

    public void init() {
        validateInitState();
        if (chessDao.isExistSavingGame()) {
            List<PieceEntity> pieceEntities = chessDao.findAllPieces();
            TurnType turn = chessDao.findCurrentTurn();
            board = toBoard(pieceEntities, turn);
            return;
        }
        board = BoardFactory.createInitBoard();
        saveBoard();
    }

    private void validateInitState() {
        if (board != null) {
            throw new IllegalStateException("보드가 이미 초기화 되었습니다.");
        }
    }

    private void saveBoard() {
        List<PieceEntity> entities = toEntities(board);
        TurnType turn = TurnType.from(board.getTurn());
        chessDao.saveBoard(entities, turn);
    }

    private Board toBoard(List<PieceEntity> pieceEntities, TurnType turnType) {
        Map<Position, Piece> board = pieceEntities.stream()
                .filter(piece -> !piece.getPieceType().isEmpty())
                .collect(Collectors.toMap(
                        PieceEntity::getPosition,
                        PieceEntity::toPiece
                ));
        Team turn = turnType.getTeam();
        return new Board(board, turn);
    }

    private List<PieceEntity> toEntities(Board board) {
        return Position.ALL_POSITIONS.stream()
                .map(this::findPieceToEntity)
                .toList();
    }

    private PieceEntity findPieceToEntity(Position position) {
        return board.find(position)
                .map(piece -> new PieceEntity(position, piece))
                .orElse(PieceEntity.createEmptyPiece(position));
    }
}
