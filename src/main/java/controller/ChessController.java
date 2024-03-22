package controller;

import dto.ChessBoardDto;
import exception.CustomException;
import java.util.Collections;
import java.util.List;
import model.Command;
import model.ChessBoard;
import model.status.GameStatus;
import model.status.Initialization;
import view.InputView;
import view.OutputView;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;

    public ChessController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.setting();
        outputView.printStartMessage();
        GameStatus gameStatus = initGame();

        while (gameStatus.isRunning()) {
            printCurrentStatus(chessBoard);
            gameStatus = play(gameStatus, chessBoard);
        }
    }

    private GameStatus initGame() {
        try {
            return Initialization.gameSetting(getCommand());
        } catch (CustomException exception) {
            outputView.printException(exception.getErrorCode());
            return initGame();
        }
    }

    private GameStatus play(GameStatus gameStatus, final ChessBoard chessBoard) {
        try {
            return gameStatus.play(getCommand(), chessBoard);
        } catch (CustomException exception) {
            outputView.printException(exception.getErrorCode());
            return play(gameStatus, chessBoard);
        }
    }

    private void printCurrentStatus(final ChessBoard chessBoard) {
        outputView.printChessBoard(ChessBoardDto.from(chessBoard));
        outputView.printCurrentCamp(chessBoard.getCamp());
    }

    private List<String> getCommand() {
        List<String> command = Collections.emptyList();
        while (command.isEmpty()) {
            command = readCommand();
        }
        return command;
    }

    private List<String> readCommand() {
        try {
            return readLine();
        } catch (CustomException exception) {
            outputView.printException(exception.getErrorCode());
        }
        return Collections.emptyList();
    }

    private List<String> readLine() {
        List<String> input = inputView.readCommandList();
        for (String value : input) {
            Command.validate(value);
        }
        return input;
    }
}
