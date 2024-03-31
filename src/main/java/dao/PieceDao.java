package dao;

import domain.board.File;
import domain.board.Rank;

import java.util.List;

public interface PieceDao {

    List<PieceEntity> findAll();

    boolean existPiecePositions();

    void save(PieceEntity piece);

    void update(File sourceFile, Rank sourceRank, File destinationFile, Rank destinationRank);

    void delete(File file, Rank rank);

    void deleteAll();
}
