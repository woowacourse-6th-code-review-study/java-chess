package service;

import db.JdbcTemplate;
import dto.PieceDto;
import dto.RoomDto;
import dto.StateDto;
import repository.GameStateDao;
import repository.PieceDao;
import repository.PieceDaoImpl;
import repository.GameStateDaoImpl;

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

    public List<PieceDto> loadPreviousPieces(final RoomDto roomDto) {
        return pieceDao.findPieceByGameId(roomDto.room_id());
    }

    public StateDto loadPreviousState(final RoomDto roomDto) {
        return gameStateDao.findByGameId(roomDto.room_id())
                .orElseThrow(NoSuchElementException::new);
    }

    public void updatePieces(final RoomDto roomDto, final List<PieceDto> pieceDtos) {
        pieceDao.deleteAllByGameId(roomDto.room_id());
        for (PieceDto pieceDto : pieceDtos) {
            pieceDao.add(roomDto, pieceDto);
        }
    }

    public void updateState(final StateDto stateDto) {
        gameStateDao.deleteByGameId(stateDto.gameId());
        gameStateDao.add(stateDto);
    }
}
