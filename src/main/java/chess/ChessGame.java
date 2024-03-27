package chess;

import chess.domain.Board;
import chess.domain.BoardFactory;
import chess.domain.Point;
import chess.domain.Team;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.dto.PieceDto;
import chess.dto.ProgressStatus;
import chess.view.GameCommand;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChessGame {

    private final InputView inputView;
    private final OutputView outputView;

    public ChessGame(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Board board = startGame();
        play(board);
    }

    private Board startGame() {
        outputView.printStartGame();
        GameCommand command = inputView.readCommand();
        if (command.isStart()) {
            Board board = BoardFactory.createInitBoard();
            showBoard(board);
            return board;
        }
        throw new IllegalArgumentException("아직 게임을 시작하지 않았습니다.");
    }

    private void play(Board board) {
        ProgressStatus status;
        do {
            status = processTurn(board);
        } while (status.isContinue());

        showResult(status);
    }

    private ProgressStatus processTurn(Board board) {
        GameCommand command = inputView.readCommand();
        if (command.isStart()) {
            throw new IllegalArgumentException("이미 게임을 시작했습니다.");
        }
        if (command.isEnd()) {
            System.exit(0);
        }
        if (command.isStatus()) {
            Map<Team, Point> status = board.calculatePoints();
            Map<Team, Double> dto = status.entrySet().stream()
                    .collect(Collectors.toMap(
                            entry -> entry.getKey(),
                            entry -> entry.getValue().toDouble()
                    ));
            outputView.printStatus(dto);
            return ProgressStatus.PROGRESS;
        }
        return executeMove(board);
    }

    private ProgressStatus executeMove(Board board) {
        Position start = inputView.readPosition();
        Position end = inputView.readPosition();
        ProgressStatus status = board.move(start, end);
        showBoard(board);
        return status;
    }

    private void showResult(ProgressStatus status) {
        outputView.printWinnerMessage(status);
    }

    private void showBoard(Board board) {
        List<Position> positions = Position.ALL_POSITIONS;
        Map<Position, PieceDto> boardDto = new HashMap<>();
        positions.forEach(position -> addPiece(board, position, boardDto));
        outputView.printBoard(boardDto);
    }

    private void addPiece(Board board, Position position, Map<Position, PieceDto> boardDto) {
        Optional<Piece> optionalPiece = board.find(position);

        if (optionalPiece.isEmpty()) {
            return;
        }

        Piece piece = optionalPiece.get();
        PieceDto pieceDto = PieceDto.from(piece);
        boardDto.put(position, pieceDto);
    }
}
