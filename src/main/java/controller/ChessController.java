package controller;

import db.Repository;
import db.dto.BoardDto;
import db.dto.MovingDto;
import db.dto.TurnDto;
import dto.ChessBoardDto;
import dto.ScoreDto;
import exception.CustomException;
import java.util.List;
import model.Board;
import model.Camp;
import model.ChessGame;
import model.command.CommandLine;
import model.status.GameStatus;
import model.status.Quit;
import model.status.StatusFactory;
import view.InputView;
import view.OutputView;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Repository repository = new Repository("chess");

    public ChessController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {

        final ChessGame chessGame = create();
        outputView.printStartMessage();
        GameStatus gameStatus = initGame();
        outputView.printChessBoard(ChessBoardDto.from(chessGame));

        while (gameStatus.isRunning()) {
            gameStatus = play(gameStatus, chessGame);
        }

        repository.remove();

        if (gameStatus instanceof Quit) { // TODO instanceof 괜춘?
            repository.removeMoving();
            return;
        }
        if (gameStatus.isCheck()) {
            repository.removeMoving();
            return; // TODO 체크로 게임이 끝났을때 어떻게 처리할까
        }
        final BoardDto boardDto = BoardDto.from(new Board(chessGame.getBoard())); // TODO 형태 너무 이상 변경 필요
        repository.save(boardDto, chessGame.getCamp());
    }

    private ChessGame create() {
        if (repository.hasGame()) {
            final BoardDto board = repository.findBoard();
            final TurnDto turn = repository.findTurn();
            return new ChessGame(board.convert(), turn.convert());
        }
        return ChessGame.setupStartingPosition();

    }

    private GameStatus initGame() {
        try {
            return StatusFactory.create(readCommandLine());
        } catch (final CustomException exception) {
            outputView.printException(exception.getErrorCode());
            return initGame();
        }
    }

    private GameStatus play(final GameStatus preStatus, final ChessGame chessGame) {
        try {
            final CommandLine commandLine = readCommandLine();
            final GameStatus status = preStatus.play(commandLine, chessGame);
            saveMoving(chessGame, commandLine);
            print(status, commandLine, chessGame);
            return status;
        } catch (final CustomException exception) {
            outputView.printException(exception.getErrorCode());
            return play(preStatus, chessGame);
        }
    }

    private void saveMoving(final ChessGame chessGame, final CommandLine commandLine) {
        if (commandLine.isMove()) {
            final List<String> body = commandLine.getBody();
            final Camp camp = chessGame.getCamp();
            final MovingDto movingDto = MovingDto.from(body, camp);
            repository.saveMoving(movingDto);
        }
    }

    //TODO 뭔가 이녀석도 옮겨주기
    private void print(final GameStatus gameStatus, final CommandLine commandLine, final ChessGame chessGame) {
        if (gameStatus.isCheck()) {
            outputView.printWinner(chessGame.getCamp().toString());
            return;
        }
        if (commandLine.isStatus()) {
            outputView.printScore(ScoreDto.from(chessGame));
        }
        if (commandLine.isStart() || commandLine.isMove()) {
            outputView.printChessBoard(ChessBoardDto.from(chessGame));
        }
    }

    private CommandLine readCommandLine() {
        try {
            final List<String> command = inputView.readCommandList();
            return CommandLine.from(command);
        } catch (final CustomException exception) {
            outputView.printException(exception.getErrorCode());
        }
        return readCommandLine();
    }
}
