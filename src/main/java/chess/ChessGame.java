package chess;

import chess.domain.Team;
import chess.domain.position.Position;
import chess.dto.PieceDto;
import chess.dto.ProgressStatus;
import chess.view.GameCommand;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.Map;

public class ChessGame {

    private final InputView inputView;
    private final OutputView outputView;
    private final ChessService chessService;

    public ChessGame(InputView inputView, OutputView outputView, ChessService chessService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.chessService = chessService;
    }

    public void run() {
        startGame();
        play();
    }

    private void startGame() {
        outputView.printStartGame();
        GameCommand command = inputView.readCommand();
        if (command.isStart()) {
            initBoard();
            showBoard();
            return;
        }
        throw new IllegalArgumentException("아직 게임을 시작하지 않았습니다.");
    }

    private void initBoard() {
        chessService.init();
    }

    private void play() {
        ProgressStatus status;
        do {
            status = processTurn();
        } while (status.isContinue());
        showResult(status);
    }

    private ProgressStatus processTurn() {
        GameCommand command = inputView.readCommand();
        if (command.isStart()) {
            throw new IllegalArgumentException("이미 게임을 시작했습니다.");
        }
        if (command.isMove()) {
            return executeMove();
        }
        if (command.isStatus()) {
            return executeStatus();
        }
        return ProgressStatus.END_GAME;
    }

    private ProgressStatus executeStatus() {
        Map<Team, Double> statusDto = chessService.calculatePiecePoints();
        outputView.printStatus(statusDto);
        return ProgressStatus.PROGRESS;
    }

    private ProgressStatus executeMove() {
        Position start = inputView.readPosition();
        Position end = inputView.readPosition();
        ProgressStatus status = chessService.moveTo(start, end);
        showBoard();
        return status;
    }

    private void showResult(ProgressStatus status) {
        if (status.isInputEndCommand()) {
            return;
        }
        outputView.printWinnerMessage(status);
    }

    private void showBoard() {
        Map<Position, PieceDto> boardDto = chessService.findTotalBoard();
        outputView.printBoard(boardDto);
    }
}
