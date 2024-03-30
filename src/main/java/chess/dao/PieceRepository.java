package chess.dao;

import java.util.List;

public interface PieceRepository {

    List<PieceEntity> findAll();

    List<PieceEntity> saveAll(List<PieceEntity> pieces);

    void update(PieceEntity piece);

    void deleteAll();
}
