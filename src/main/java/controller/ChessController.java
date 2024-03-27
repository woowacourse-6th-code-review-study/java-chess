package controller;

import dto.ChessBoardDto;
import dto.ScoreDto;
import exception.CustomException;
import java.util.List;
import model.ChessGame;
import model.command.CommandLine;
import model.status.GameStatus;
import model.status.StatusFactory;
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
        final ChessGame chessGame = ChessGame.setupStartingPosition();
        outputView.printStartMessage();
        GameStatus gameStatus = initGame();

        while (gameStatus.isRunning()) {
            printChessBoard(chessGame);
            gameStatus = play(gameStatus, chessGame);
        }
    }

    private GameStatus initGame() {
        try {
            return StatusFactory.create(readCommandLine());
        } catch (final CustomException exception) {
            outputView.printException(exception.getErrorCode());
            return initGame();
        }
    }

    private GameStatus play(final GameStatus gameStatus, final ChessGame chessGame) {
        try {
            final CommandLine commandLine = readCommandLine();
            printStatusOrNot(chessGame, commandLine);
            return gameStatus.play(commandLine, chessGame);
        } catch (final CustomException exception) {
            outputView.printException(exception.getErrorCode());
            return play(gameStatus, chessGame);
        }
    }

    //TODO 메서드 이름 이상해ㅐㅐㅐㅐ 형태도 이상해
    private void printStatusOrNot(final ChessGame chessGame, final CommandLine commandLine) {
        if (commandLine.isStatus()) {
            outputView.printScore(ScoreDto.from(chessGame));
        }
    }

    private void printChessBoard(final ChessGame chessGame) {
        outputView.printChessBoard(ChessBoardDto.from(chessGame));
    }

    private CommandLine readCommandLine() {
        try {
            List<String> command = inputView.readCommandList();
            return CommandLine.from(command);
        } catch (final CustomException exception) {
            outputView.printException(exception.getErrorCode());
        }
        return readCommandLine();
    }
}
