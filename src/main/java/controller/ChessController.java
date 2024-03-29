package controller;

import db.MovingDao;
import db.dto.BoardDto;
import db.dto.MovingDto;
import db.dto.PieceDto;
import db.dto.PositionDto;
import db.dto.TurnDto;
import dto.ChessBoardDto;
import dto.ScoreDto;
import exception.CustomException;
import java.util.List;
import java.util.Map;
import model.Board;
import model.ChessGame;
import model.command.CommandLine;
import model.status.GameStatus;
import model.status.StatusFactory;
import view.InputView;
import view.OutputView;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;
    private final MovingDao movingDao = new MovingDao("chess");

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

        final BoardDto boardDto = BoardDto.from(new Board(chessGame.getBoard())); // TODO 형태 너무 이상 변경 필요
        movingDao.remove("board");
        movingDao.remove("turn");
        movingDao.createBoard();
        movingDao.createTurn();

        movingDao.addBoard(boardDto);
        movingDao.addTurn(chessGame.getCamp());
    }

    private ChessGame create() {
        final BoardDto board = movingDao.findBoard();
        final TurnDto turn = movingDao.findTurn();
        final Map<PositionDto, PieceDto> pieces = board.pieces();

        if (pieces.isEmpty() || turn == null) {
            return ChessGame.setupStartingPosition();
        }
        return new ChessGame(board.convert(), turn.convert());
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
            final MovingDto movingDto = getMovingDto(chessGame, commandLine);
            movingDao.addMoving(movingDto);
        }
    }

    private MovingDto getMovingDto(final ChessGame chessGame, final CommandLine commandLine) {
        final String camp = chessGame.getCamp().toString();
        final List<String> body = commandLine.getBody();

        final List<String> current = List.of(body.get(0).split(""));
        final List<String> next = List.of(body.get(1).split(""));

        return new MovingDto(camp, current.get(0), current.get(1), next.get(0), next.get(1));
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
