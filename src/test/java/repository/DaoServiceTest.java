package repository;

import domain.piece.Color;
import dto.PieceDto;
import dto.TurnDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DaoServiceTest {
    private static final PieceDto A2WhitePawn = new PieceDto("A", "2", "WHITE", "PAWN");
    private static final PieceDto B2WhitePawn = new PieceDto("B", "2", "WHITE", "PAWN");
    private static final PieceDto C2WhitePawn = new PieceDto("C", "2", "WHITE", "PAWN");
    private static final TurnDto whiteTurn = TurnDto.of(Color.WHITE);
    private static final TurnDto blackTurn = TurnDto.of(Color.BLACK);

    private final PieceDao pieceDao = new PieceDao();
    private final TurnDao turnDao = new TurnDao();
    private final DaoService daoService = new DaoService();

    @BeforeEach
    void setData() {
        pieceDao.add(A2WhitePawn);
        pieceDao.add(B2WhitePawn);
        pieceDao.add(C2WhitePawn);
        turnDao.update(whiteTurn);
    }

    @AfterEach
    void rollback() {
        pieceDao.deleteAll();
        turnDao.deleteAll();
    }

    @Test
    void 이전_게임_데이터가_존재한다() {
        assertThat(daoService.isPreviousDataExist()).isTrue();
    }

    @Test
    void 이전_게임_데이터를_불러온다() {
        assertThat(daoService.loadPreviousData())
                .containsExactlyInAnyOrder(A2WhitePawn, B2WhitePawn, C2WhitePawn);
    }

    @Test
    void 이전_게임의_턴_데이터를_불러온다() {
        assertThat(daoService.loadPreviousTurn().getTurn())
                .isEqualTo(Color.WHITE);
    }

    @Test
    void 피스_데이터를_갱신한다() {
        PieceDto A3WhitePawn = new PieceDto("A", "3", "WHITE", "PAWN");
        PieceDto B3WhitePawn = new PieceDto("B", "3", "WHITE", "PAWN");
        PieceDto C3WhitePawn = new PieceDto("C", "3", "WHITE", "PAWN");

        List<PieceDto> pieceDtos = List.of(A3WhitePawn, B3WhitePawn, C3WhitePawn);

        daoService.updatePiece(pieceDtos);

        assertThat(daoService.loadPreviousData())
                .containsExactlyInAnyOrder(A3WhitePawn, B3WhitePawn, C3WhitePawn);
    }

    @Test
    void 턴_데이터를_갱신한다() {
        daoService.updateTurn(blackTurn);

        assertThat(daoService.loadPreviousTurn().getTurn())
                .isEqualTo(Color.BLACK);
    }

    @Test
    void 모든_데이터를_삭제한다() {
        daoService.deletePreviousData();

        assertThat(daoService.isPreviousDataExist()).isFalse();
    }
}
