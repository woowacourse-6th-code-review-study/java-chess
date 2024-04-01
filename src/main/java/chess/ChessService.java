package chess;

import chess.dao.ChessDao;
import chess.dao.PieceEntity;
import chess.domain.Board;
import chess.domain.BoardFactory;
import chess.domain.Point;
import chess.domain.Team;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.dto.PieceDto;
import chess.dto.ProgressStatus;
import chess.dto.TurnType;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

    private Board toBoard(List<PieceEntity> pieceEntities, TurnType turnType) {
        Map<Position, Piece> board = pieceEntities.stream()
                .filter(PieceEntity::isExistPiece)
                .collect(Collectors.toMap(
                        PieceEntity::getPosition,
                        PieceEntity::toPiece
                ));
        Team turn = turnType.getTeam();
        return new Board(board, turn);
    }

    private void saveBoard() {
        List<PieceEntity> entities = findEntities();
        TurnType turn = TurnType.from(board.findCurrentTurn());
        chessDao.saveBoard(entities, turn);
    }

    private List<PieceEntity> findEntities() {
        return Position.ALL_POSITIONS.stream()
                .map(this::findPieceToEntity)
                .toList();
    }

    public ProgressStatus moveTo(Position start, Position end) {
        ProgressStatus progressStatus = board.move(start, end);

        if (progressStatus.isContinue()) {
            saveMoving(start, end);
            return progressStatus;
        }
        chessDao.deleteAll();
        return progressStatus;
    }

    private void saveMoving(Position start, Position end) {
        PieceEntity movedPiece = findPieceToEntity(end);
        TurnType turnType = TurnType.from(board.findCurrentTurn());
        chessDao.saveMoving(movedPiece, start, turnType);
    }

    public Map<Position, PieceDto> findTotalBoard() {
        return Position.ALL_POSITIONS.stream()
                .map(this::toResultEntry)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private Entry<Position, PieceDto> toResultEntry(Position position) {
        PieceDto pieceDto = board.find(position)
                .map(PieceDto::from)
                .orElse(PieceDto.createEmptyPiece());
        return Map.entry(position, pieceDto);
    }

    private PieceEntity findPieceToEntity(Position position) {
        return board.find(position)
                .map(piece -> new PieceEntity(position, piece))
                .orElse(PieceEntity.createEmptyPiece(position));
    }

    public Map<Team, Double> calculatePiecePoints() {
        Map<Team, Point> status = board.calculateTotalPoints();
        return toDto(status);
    }

    private Map<Team, Double> toDto(Map<Team, Point> status) {
        return status.entrySet().stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        entry -> entry.getValue().toDouble()
                ));
    }

    public Team findCurrentTurn() {
        return board.findCurrentTurn();
    }
}
