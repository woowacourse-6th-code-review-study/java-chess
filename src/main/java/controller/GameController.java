package controller;

import domain.board.Position;
import domain.game.*;
import domain.piece.PieceColor;
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

    public void buildGame() {
        if (chessGame.existPrevGame()) {
            outputView.printInputRoadGameMessage();
            GameCommand gameCommand = inputCommand();
            gameCommand.execute(this);
            return;
        }
        createNewGame();
        start();
    }

    public void createNewGame() {
        chessGame.createNewGame();
    }

    public void roadPrevGame() {
        chessGame.roadPrevGame();
    }

    public void start() {
        chessGame.gameStart();
        outputView.printWelcomeMessage();
        while (chessGame.isRunning()) {
            BoardDto boardDto = BoardDto.from(chessGame.piecePositions());
            PieceColor currentPlayTeamColor = chessGame.currentPlayTeamColor();
            outputView.printTurnStatus(boardDto, currentPlayTeamColor);
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

    public void movePiece(final Position source, final Position destination) {
        chessGame.movePiece(source, destination);
    }

    public GameStatus gameStatus() {
        return chessGame.gameStatus();
    }

    public void end() {
        chessGame.gameEnd();
    }

    public void printGameStatus() {
        GameScore gameScore = chessGame.getGameResult();
        outputView.printGameResult(
                gameScore.whiteTeamScore(),
                gameScore.blackTeamScore(),
                gameScore.gameResult());
    }
}
