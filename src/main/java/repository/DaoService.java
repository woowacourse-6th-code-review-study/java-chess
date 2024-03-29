package repository;

import db.JdbcTemplate;
import dto.PieceDto;
import dto.StateDto;

import java.util.List;

public class DaoService {
    private final PieceDao pieceDao;
    private final StateDao stateDao;

    public DaoService() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate();
        this.pieceDao = new PieceDao(jdbcTemplate);
        this.stateDao = new StateDao(jdbcTemplate);
    }

    public boolean isPreviousDataExist() {
        return pieceDao.hasRecords();
    }

    public List<PieceDto> loadPreviousData() {
        return pieceDao.findAll();
    }

    public StateDto loadPreviousTurn() {
        return stateDao.find();
    }

    public void updatePiece(final List<PieceDto> pieceDtos) {
        pieceDao.deleteAll();
        for (PieceDto pieceDto : pieceDtos) {
            pieceDao.add(pieceDto);
        }
    }

    public void updateTurn(final StateDto stateDto) {
        stateDao.deleteAll();
        stateDao.update(stateDto);
    }

    public void deletePreviousData() {
        pieceDao.deleteAll();
        stateDao.deleteAll();
    }
}
