package chess.service;

import static chess.domain.Position.A1;
import static chess.domain.piece.Team.BLACK;
import static chess.domain.piece.Team.WHITE;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.dao.FakePiecesOnChessBoardDAO;
import chess.dao.FakeTurnDAO;
import chess.domain.game.ChessGame;
import chess.domain.game.command.MoveCommand;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessPersistenceServiceTest {

    @Test
    @DisplayName("저장된 게임이 있는 경우, 잘 불러오는지 확인")
    void loadChessGame() {
        FakePiecesOnChessBoardDAO piecesOnChessBoardDAO = new FakePiecesOnChessBoardDAO();
        FakeTurnDAO turnDAO = new FakeTurnDAO();
        ChessPersistenceService chessPersistenceService = new ChessPersistenceService(piecesOnChessBoardDAO, turnDAO);
        List<Piece> pieces = List.of(new Pawn(A1, WHITE));
        Team currentTeam = BLACK;
        ChessGame chessGame = new ChessGame(pieces, currentTeam);
        chessPersistenceService.saveChessGame(chessGame);

        ChessGame loadedChessGame = chessPersistenceService.loadChessGame();
        List<Piece> loadedPieces = loadedChessGame.getPiecesOnBoard();
        Team loadedCurrentTeam = loadedChessGame.currentTeam();

        assertAll(
                () -> Assertions.assertThat(loadedPieces).containsExactlyInAnyOrderElementsOf(pieces),
                () -> Assertions.assertThat(loadedCurrentTeam).isEqualTo(currentTeam)
        );

    }

    @Test
    @DisplayName("저장된 게임이 있는 경우 저장된 게임이 있다고 하는지 검증")
    void isSaveDataExistWhenExist() {
        FakePiecesOnChessBoardDAO piecesOnChessBoardDAO = new FakePiecesOnChessBoardDAO();
        FakeTurnDAO turnDAO = new FakeTurnDAO();
        ChessPersistenceService chessPersistenceService = new ChessPersistenceService(piecesOnChessBoardDAO, turnDAO);
        ChessGame chessGame = new ChessGame();
        chessPersistenceService.saveChessGame(chessGame);

        boolean saveDataExist = chessPersistenceService.isSaveDataExist();

        Assertions.assertThat(saveDataExist)
                .isTrue();
    }

    @Test
    @DisplayName("저장된 게임이 없는 경우 저장된 게임이 없다고 하는지 검증")
    void isSaveDataExistWhenNotExist() {
        FakePiecesOnChessBoardDAO piecesOnChessBoardDAO = new FakePiecesOnChessBoardDAO();
        FakeTurnDAO turnDAO = new FakeTurnDAO();
        ChessPersistenceService chessPersistenceService = new ChessPersistenceService(piecesOnChessBoardDAO, turnDAO);

        boolean saveDataExist = chessPersistenceService.isSaveDataExist();

        Assertions.assertThat(saveDataExist)
                .isFalse();
    }

    @Test
    @DisplayName("게임이 잘 저장이 되는지 검증")
    void saveChessGame() {
        FakePiecesOnChessBoardDAO piecesOnChessBoardDAO = new FakePiecesOnChessBoardDAO();
        FakeTurnDAO turnDAO = new FakeTurnDAO();
        ChessPersistenceService chessPersistenceService = new ChessPersistenceService(piecesOnChessBoardDAO, turnDAO);
        boolean saveChessGameSuccess = chessPersistenceService.saveChessGame(new ChessGame());
        Assertions.assertThat(saveChessGameSuccess)
                .isTrue();
    }

    @Test
    @DisplayName("이미 저장된 게임이 있으면 게임이 저장되지 않는지 검증")
    void saveChessGameFail() {
        FakePiecesOnChessBoardDAO piecesOnChessBoardDAO = new FakePiecesOnChessBoardDAO();
        FakeTurnDAO turnDAO = new FakeTurnDAO();
        ChessPersistenceService chessPersistenceService = new ChessPersistenceService(piecesOnChessBoardDAO, turnDAO);
        chessPersistenceService.saveChessGame(new ChessGame());

        boolean saveChessGameSuccess = chessPersistenceService.saveChessGame(new ChessGame());
        Assertions.assertThat(saveChessGameSuccess)
                .isFalse();
    }

    @Test
    @DisplayName("게임의 진행 상황을 저장할 수 있는지 검증")
    void updateChessGame() {
        FakePiecesOnChessBoardDAO piecesOnChessBoardDAO = new FakePiecesOnChessBoardDAO();
        FakeTurnDAO turnDAO = new FakeTurnDAO();
        ChessPersistenceService chessPersistenceService = new ChessPersistenceService(piecesOnChessBoardDAO, turnDAO);
        ChessGame chessGame = new ChessGame();
        chessPersistenceService.saveChessGame(chessGame);
        MoveCommand moveCommand = new MoveCommand("a2", "a4");
        chessGame.move(moveCommand);
        boolean updateChessGameSuccess = chessPersistenceService.updateChessGame(chessGame, moveCommand);
        Assertions.assertThat(updateChessGameSuccess)
                .isTrue();
    }
}
