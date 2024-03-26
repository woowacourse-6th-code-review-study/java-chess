package controller;

import domain.board.Position;
import domain.game.ChessGame;
import domain.game.GameCommand;
import domain.game.GameCommandType;
import domain.game.GameStatus;
import dto.BoardDto;
import view.InputView;
import view.OutputView;

public class GameController {
    private final InputView inputView;
    private final OutputView outputView;
    private final ChessGame chessGame;

    public GameController(final InputView inputView, final OutputView outputView, final ChessGame chessGame) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.chessGame = chessGame;
    }

    public void run() {
        initGame();

        if (chessGame.isRunning()) {
            start();
        }
    }

    private void initGame() {
        try {
            GameCommand gameCommand = inputCommand();
            gameCommand.execute(this);
        } catch (Exception e) {
            outputView.printErrorMessage(e.getMessage());
            initGame();
        }
    }

    private GameCommand inputCommand() {
        String[] inputValues = inputView.inputCommand().split(" ");
        return GameCommandType.of(inputValues);
    }

    public void start() {
        chessGame.gameStart();
        outputView.printWelcomeMessage();
        while (chessGame.isRunning()) {
            BoardDto boardDto = BoardDto.from(chessGame.piecePositions());
            outputView.printBoard(boardDto);
            playTurn();
        }
    }

    private void playTurn() {
        try {
            GameCommand gameCommand = inputCommand();
            gameCommand.execute(this);
        } catch (Exception e) {
            outputView.printErrorMessage(e.getMessage());
            playTurn();
        }
    }

    public GameStatus gameStatus() {
        return chessGame.gameStatus();
    }

    public void movePiece(final Position source, final Position destination) {
        chessGame.movePiece(source, destination);
    }

    public void end() {
        chessGame.gameEnd();
    }
}
