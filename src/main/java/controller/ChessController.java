package controller;

import domain.ChessGame;
import domain.board.ChessBoard;
import domain.board.ChessBoardFactory;
import dto.TurnDto;
import repository.DaoService;
import view.InputView;
import view.OutputView;

public class ChessController {
    private final InputView inputView;
    private final OutputView outputView;

    public ChessController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        DaoService daoService = new DaoService();

        outputView.printGameGuideMessage();
        ChessGame chessGame = new ChessGame(initializeChessGame(daoService));

        while (chessGame.isPlaying()) {
            readCommandUntilValid(chessGame);
        }

        updateGameStatus(daoService, chessGame);
    }

    private ChessBoard initializeChessGame(DaoService daoService) {
        try {
            TurnDto turnDto = daoService.loadPreviousTurn();
            return ChessBoardFactory.loadPreviousChessBoard(daoService.loadPreviousData(), turnDto.getTurn());
        } catch (IllegalArgumentException e) {
            return ChessBoardFactory.createInitialChessBoard();
        }
    }

    private void readCommandUntilValid(ChessGame game) {
        try {
            inputView.readCommand().execute(game, outputView);
        } catch (Exception e) {
            outputView.printErrorMessage(e.getMessage());
            readCommandUntilValid(game);
        }
    }

    private void updateGameStatus(DaoService daoService, ChessGame game) {
        if (game.isGameOver()) {
            daoService.deletePreviousData();
            return;
        }
        daoService.updatePiece(game.getBoard().getPieces());
        daoService.updateTurn(TurnDto.of(game.getTurn()));
    }
}


/*
커넥션을 DAO에 넣어주기
트랜잭셔널 구현 가능
테스트도 가능할듯

DAO를 테스트하는 이유
- JDBC를 제대로 썼냐
- 쿼리를 제대로 썼냐


 */
