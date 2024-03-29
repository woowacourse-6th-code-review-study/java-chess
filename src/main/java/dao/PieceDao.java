package dao;

import domain.board.File;
import domain.board.Rank;

import java.util.List;

public interface PieceDao {

    List<PieceEntity> findAll();

    boolean existPiecePositions();

    void savePiece(PieceEntity piece);

    void updatePiecePosition(File sourceFile, Rank sourceRank, File destinationFile, Rank destinationRank);

    void deleteByFileAndRank(File file, Rank rank);

    void deleteAll();
}
