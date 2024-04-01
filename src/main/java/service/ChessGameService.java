package service;

import domain.ChessGame;
import domain.board.ChessBoardFactory;
import dto.PieceDto;
import dto.RoomDto;
import dto.StateDto;
import database.dao.GameStateDao;
import database.dao.PieceDao;

import java.util.List;
import java.util.NoSuchElementException;

public class ChessGameService {
    private final PieceDao pieceDao;
    private final GameStateDao gameStateDao;

    public ChessGameService(final PieceDao pieceDao, final GameStateDao gameStateDao) {
        this.pieceDao = pieceDao;
        this.gameStateDao = gameStateDao;
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
