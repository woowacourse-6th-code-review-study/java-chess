package repository;

import db.JdbcTemplate;
import dto.PieceDto;
import dto.RoomDto;
import dto.TurnDto;

import java.util.List;

public class ChessGameService {
    private final PieceDao pieceDao;
    private final TurnDao turnDao;

    ChessGameService(final PieceDao pieceDao, final TurnDao turnDao) {
        this.pieceDao = pieceDao;
        this.turnDao = turnDao;
    }

    public ChessGameService() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate();
        this.pieceDao = new PieceDao(jdbcTemplate);
        this.turnDao = new TurnDao(jdbcTemplate);
    }

    public boolean isPreviousDataExist() {
        return pieceDao.hasRecords();
    }

    public List<PieceDto> loadPreviousData(final RoomDto roomDto) {
        return pieceDao.findPieceByGameId(roomDto.room_id());
    }

    public TurnDto loadPreviousTurn(final RoomDto roomDto) {
        return turnDao.findTurnByGameId(roomDto);
    }

    public void updatePiece(final RoomDto roomDto, final List<PieceDto> pieceDtos) {
        pieceDao.deleteAll(roomDto);
        for (PieceDto pieceDto : pieceDtos) {
            pieceDao.add(roomDto, pieceDto);
        }
    }

    public void updateTurn(final RoomDto roomDto, final TurnDto turnDto) {
        turnDao.delete(roomDto);
        turnDao.add(turnDto);
    }
}
