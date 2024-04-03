package domain.board;

import dao.PieceDao;
import dao.PieceEntity;

import java.util.Collections;
import java.util.List;

public class TestPieceDao implements PieceDao {

    @Override
    public List<PieceEntity> findAll() {
        return Collections.emptyList();
    }

    @Override
    public boolean existPiecePositions() {
        return false;
    }

    @Override
    public void save(final PieceEntity piece) {

    }

    @Override
    public void update(final File sourceFile, final Rank sourceRank, final File destinationFile, final Rank destinationRank) {

    }

    @Override
    public void delete(final File file, final Rank rank) {

    }

    @Override
    public void deleteAll() {

    }
}
