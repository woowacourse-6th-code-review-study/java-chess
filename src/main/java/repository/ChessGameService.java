package repository;

import db.JdbcTemplate;
import dto.PieceDto;
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

    public List<PieceDto> loadPreviousData() {
        return pieceDao.findAll();
    }

    public TurnDto loadPreviousTurn() {
        return turnDao.findOne();
    }

    public void updatePiece(final List<PieceDto> pieceDtos) {
        pieceDao.deleteAll();
        for (PieceDto pieceDto : pieceDtos) {
            pieceDao.add(pieceDto);
        }
    }

    public void updateTurn(final TurnDto turnDto) {
        turnDao.deleteAll();
        turnDao.update(turnDto);
    }

    public void deletePreviousData() {
        pieceDao.deleteAll();
        turnDao.deleteAll();
    }
}
