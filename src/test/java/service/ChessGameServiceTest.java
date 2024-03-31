package service;

import domain.piece.Color;
import dto.PieceDto;
import dto.RoomDto;
import dto.StateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import repository.GameStateDao;
import repository.GameStateMockDao;
import repository.PieceDao;
import repository.PieceMockDao;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ChessGameServiceTest {
    private static final PieceDto A2WhitePawn = new PieceDto("A", "2", "WHITE", "PAWN");
    private static final PieceDto B2WhitePawn = new PieceDto("B", "2", "WHITE", "PAWN");
    private static final PieceDto C2WhitePawn = new PieceDto("C", "2", "WHITE", "PAWN");
    private static final StateDto whiteTurn = new StateDto("WHITE", 1);
    private static final StateDto blackTurn = new StateDto("BLACK", 2);
    private static final RoomDto room1 = new RoomDto(1);

    private final PieceDao pieceDao = new PieceMockDao();
    private final GameStateDao gameStateDao = new GameStateMockDao();
    private final ChessGameService chessGameService = new ChessGameService(pieceDao, gameStateDao);

    @BeforeEach
    void setData() {
        pieceDao.add(room1, A2WhitePawn);
        pieceDao.add(room1, B2WhitePawn);
        pieceDao.add(room1, C2WhitePawn);
        gameStateDao.add(whiteTurn);
    }

    @AfterEach
    void rollback() {
        pieceDao.deleteAllByGameId(1);
        gameStateDao.deleteByGameId(1);
    }

    @Test
    void 이전_게임의_피스_데이터를_불러온다() {
        assertThat(chessGameService.loadPreviousPieces(room1))
                .containsExactlyInAnyOrder(A2WhitePawn, B2WhitePawn, C2WhitePawn);
    }

    @Test
    void 이전_게임의_턴_데이터를_불러온다() {
        assertThat(chessGameService.loadPreviousState(room1).getState())
                .isEqualTo(Color.WHITE);
    }

    @Test
    void 피스_데이터를_갱신한다() {
        RoomDto roomDto = room1;
        PieceDto A3WhitePawn = new PieceDto("A", "3", "WHITE", "PAWN");
        PieceDto B3WhitePawn = new PieceDto("B", "3", "WHITE", "PAWN");
        PieceDto C3WhitePawn = new PieceDto("C", "3", "WHITE", "PAWN");

        chessGameService.updatePieces(roomDto, List.of(A3WhitePawn, B3WhitePawn, C3WhitePawn));

        assertThat(chessGameService.loadPreviousPieces(roomDto))
                .containsExactlyInAnyOrder(A3WhitePawn, B3WhitePawn, C3WhitePawn);
    }

    @Test
    void 턴_데이터를_갱신한다() {
        RoomDto room = new RoomDto(blackTurn.gameId());

        chessGameService.updateState(blackTurn);

        assertThat(chessGameService.loadPreviousState(room).getState())
                .isEqualTo(Color.BLACK);
    }
}
