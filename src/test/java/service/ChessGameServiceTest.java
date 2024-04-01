package service;

import domain.ChessGame;
import domain.board.ChessBoard;
import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.nonpawn.King;
import domain.piece.pawn.WhitePawn;
import domain.position.Position;
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

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ChessGameServiceTest {
    private static final PieceDto A2WhitePawn = new PieceDto("A", "2", "WHITE", "PAWN");
    private static final PieceDto B2WhitePawn = new PieceDto("B", "2", "WHITE", "PAWN");
    private static final PieceDto C2WhitePawn = new PieceDto("C", "2", "WHITE", "PAWN");
    private static final RoomDto room1 = new RoomDto(1);

    private final PieceDao pieceDao = new PieceMockDao();
    private final GameStateDao gameStateDao = new GameStateMockDao();
    private final ChessGameService chessGameService = new ChessGameService(pieceDao, gameStateDao);

    @BeforeEach
    void setData() {
        pieceDao.add(room1, A2WhitePawn);
        pieceDao.add(room1, B2WhitePawn);
        pieceDao.add(room1, C2WhitePawn);
        gameStateDao.add(new StateDto("WHITE", 1));
    }

    @AfterEach
    void rollback() {
        pieceDao.deleteAllByGameId(1);
        gameStateDao.deleteByGameId(1);
    }

    @Test
    void 이전_게임의_피스_데이터를_불러온다() {
        ChessGame chessGame = chessGameService.initializeChessGame(room1);

        assertThat(chessGame.getBoard().getPieces())
                .containsExactlyInAnyOrder(A2WhitePawn, B2WhitePawn, C2WhitePawn);
    }

    @Test
    void 이전_게임의_턴_데이터를_불러온다() {
        ChessGame chessGame = chessGameService.initializeChessGame(room1);

        assertThat(chessGame.getTurn())
                .isEqualTo(Color.WHITE);
    }

    @Test
    void 게임_데이터를_갱신한다() {
        RoomDto roomDto = room1;
        Position A3 = new Position("A3");
        Position B3 = new Position("B3");
        Position C3 = new Position("C3");
        Map<Position, Piece> pawnMap = Map.of(
                A3, new King(Color.WHITE),
                B3, new King(Color.BLACK),
                C3, new WhitePawn());
        ChessGame chessGame = new ChessGame(new ChessBoard(pawnMap));

        chessGameService.saveChessGame(chessGame, roomDto);
        chessGame = chessGameService.initializeChessGame(roomDto);

        assertThat(chessGame.getBoard().getPieces())
                .containsExactlyInAnyOrder(
                        PieceDto.of(A3, new King(Color.WHITE)),
                        PieceDto.of(B3, new King(Color.BLACK)),
                        PieceDto.of(C3, new WhitePawn()));
        assertThat(chessGame.getBoard().getTurn())
                .isEqualTo(Color.WHITE);
    }
}
