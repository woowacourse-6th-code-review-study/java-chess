package service;

import db.JdbcTemplate;
import domain.ChessGame;
import domain.board.ChessBoardFactory;
import dto.PieceDto;
import dto.RoomDto;
import dto.StateDto;
import repository.GameStateDao;
import repository.GameStateDaoImpl;
import repository.PieceDao;
import repository.PieceDaoImpl;

import java.util.List;
import java.util.NoSuchElementException;

public class ChessGameService {
    private final PieceDao pieceDao;
    private final GameStateDao gameStateDao;

    ChessGameService(final PieceDao pieceDao, final GameStateDao gameStateDao) {
        this.pieceDao = pieceDao;
        this.gameStateDao = gameStateDao;
    }

    public ChessGameService() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate();
        this.pieceDao = new PieceDaoImpl(jdbcTemplate);
        this.gameStateDao = new GameStateDaoImpl(jdbcTemplate);
    }

    public ChessGame initializeChessGame(RoomDto roomDto) {
        try {
            StateDto stateDto = loadPreviousState(roomDto);
            return new ChessGame(ChessBoardFactory.loadPreviousChessBoard(
                    loadPreviousPieces(roomDto), stateDto.getState()));
        } catch (NoSuchElementException e) {
            return new ChessGame(ChessBoardFactory.createInitialChessBoard());
        }
    }

    public void saveChessGame(ChessGame chessGame, RoomDto roomDto) {
        if (chessGame.isGameOver()) {
            updateState(new StateDto("GAMEOVER", roomDto.room_id()));
            return;
        }
        updatePieces(roomDto, chessGame.getBoard().getPieces());
        updateState(new StateDto(chessGame.getTurn().name(), roomDto.room_id()));
    }

    private List<PieceDto> loadPreviousPieces(final RoomDto roomDto) {
        return pieceDao.findPieceByGameId(roomDto.room_id());
    }

    private StateDto loadPreviousState(final RoomDto roomDto) {
        return gameStateDao.findByGameId(roomDto.room_id())
                .orElseThrow(NoSuchElementException::new);
    }

    private void updatePieces(final RoomDto roomDto, final List<PieceDto> pieceDtos) {
        pieceDao.deleteAllByGameId(roomDto.room_id());
        for (PieceDto pieceDto : pieceDtos) {
            pieceDao.add(roomDto, pieceDto);
        }
    }

    private void updateState(final StateDto stateDto) {
        gameStateDao.deleteByGameId(stateDto.gameId());
        gameStateDao.add(stateDto);
    }
}
