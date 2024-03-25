package db;

import domain.dto.PieceDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DBServiceTest {
    private static final PieceDto A2WhitePawn = new PieceDto("A", "2", "WHITE", "PAWN");
    private static final PieceDto B2WhitePawn = new PieceDto("B", "2", "WHITE", "PAWN");
    private static final PieceDto C2WhitePawn = new PieceDto("C", "2", "WHITE", "PAWN");

    private final PieceDao pieceDao = new PieceDao();
    private final DBService dbService = new DBService();

    @BeforeEach
    void setData() {
        pieceDao.add(A2WhitePawn);
        pieceDao.add(B2WhitePawn);
        pieceDao.add(C2WhitePawn);
    }

    @AfterEach
    void rollback() {
        pieceDao.deleteAll();
    }

    @Test
    void 이전_게임_데이터가_존재한다() {
        assertThat(dbService.doesPreviousDataExist()).isTrue();
    }

    @Test
    void 이전_게임_데이터를_불러온다() {
        assertThat(dbService.loadPreviousData())
                .containsExactlyInAnyOrder(A2WhitePawn, B2WhitePawn, C2WhitePawn);
    }

    @Test
    void 피스_데이터를_갱신한다() {
        PieceDto A3WhitePawn = new PieceDto("A", "3", "WHITE", "PAWN");
        PieceDto B3WhitePawn = new PieceDto("B", "3", "WHITE", "PAWN");
        PieceDto C3WhitePawn = new PieceDto("C", "3", "WHITE", "PAWN");

        List<PieceDto> pieceDtos = List.of(A3WhitePawn, B3WhitePawn, C3WhitePawn);

        dbService.updatePiece(pieceDtos);

        assertThat(dbService.loadPreviousData())
                .containsExactlyInAnyOrder(A3WhitePawn, B3WhitePawn, C3WhitePawn);
    }
}
