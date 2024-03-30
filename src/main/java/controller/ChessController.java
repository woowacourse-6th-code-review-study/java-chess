package controller;

import domain.ChessGame;
import domain.board.ChessBoard;
import domain.board.ChessBoardFactory;
import dto.TurnDto;
import repository.ChessGameService;
import view.InputView;
import view.OutputView;

public class ChessController {
    private final ChessGameService chessGameService;
    private ChessGame chessGame;

    public ChessController(final ChessGameService chessGameService) {
        this.chessGameService = chessGameService;
    }

    public void start() {
        chessGame = new ChessGame(initializeChessGame());
        OutputView.printGameGuideMessage();
        while (chessGame.isPlaying()) {
            readCommandUntilValid();
        }
        updateGameStatus();
    }

    private ChessBoard initializeChessGame() {
        try {
            TurnDto turnDto = chessGameService.loadPreviousTurn();
            return ChessBoardFactory.loadPreviousChessBoard(chessGameService.loadPreviousData(), turnDto.getTurn());
        } catch (IllegalArgumentException e) {
            return ChessBoardFactory.createInitialChessBoard();
        }
    }

    private void readCommandUntilValid() {
        try {
            InputView.readCommand().execute(chessGame);
        } catch (Exception e) {
            OutputView.printErrorMessage(e);
            readCommandUntilValid();
        }
    }

    private void updateGameStatus() {
        if (chessGame.isGameOver()) {
            chessGameService.deletePreviousData();
            return;
        }
        chessGameService.updatePiece(chessGame.getBoard().getPieces());
        chessGameService.updateTurn(TurnDto.of(chessGame.getTurn()));
    }
}
