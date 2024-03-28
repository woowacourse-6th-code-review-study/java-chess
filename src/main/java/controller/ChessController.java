package controller;

import controller.command.Command;
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
        ChessBoard board = createChessBoard(daoService);

        outputView.printGameGuideMessage();
        Command command;
        do {
            command = readCommandUntilValid();
            command.execute(board, outputView);
        } while (board.isGameRunning());

        updateGameStatus(daoService, board);
    }

    private ChessBoard createChessBoard(DaoService daoService) {
        if (daoService.isPreviousDataExist()) {
            TurnDto turnDto = daoService.loadPreviousTurn();
            return ChessBoardFactory.loadPreviousChessBoard(daoService.loadPreviousData(), turnDto.getTurn());
        }
        return ChessBoardFactory.createInitialChessBoard();
    }

    private Command readCommandUntilValid() {
        try {
            return inputView.readCommand();
        } catch (Exception e) {
            outputView.printErrorMessage(e.getMessage());
            return readCommandUntilValid();
        }
    }

    private void updateGameStatus(final DaoService daoService, final ChessBoard board) {
        if (board.isKingNotExist()) {
            daoService.deletePreviousData();
            return;
        }
        daoService.updatePiece(board.getPieces());
        daoService.updateTurn(board.getStatus());
    }
}
