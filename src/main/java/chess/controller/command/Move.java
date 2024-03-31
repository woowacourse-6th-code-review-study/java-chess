package chess.controller.command;

import chess.controller.State;
import chess.domain.board.position.Column;
import chess.domain.board.position.Position;
import chess.domain.board.position.Row;
import chess.service.BoardService;
import chess.service.GameService;
import chess.service.dto.ChessGameResult;
import chess.view.OutputView;
import chess.view.mapper.ColumnMapper;
import chess.view.mapper.RowMapper;
import java.util.List;
import java.util.regex.Pattern;

public class Move implements Command {

    private static final Pattern POSITION_REGEX = Pattern.compile(""
            + "move\\s+([a-h][1-8])\\s+([a-h][1-8])");

    private final Position from;
    private final Position to;

    public Move(List<String> commandInput) {
        validateMoveCommandPattern(commandInput);
        String fromPosition = commandInput.get(1);
        String toPosition = commandInput.get(2);
        this.from = createPosition(fromPosition.substring(0, 1), fromPosition.substring(1, 2));
        this.to = createPosition(toPosition.substring(0, 1), toPosition.substring(1, 2));
    }

    private void validateMoveCommandPattern(List<String> commandInput) {
        String moveCommand = String.join(" ", commandInput);
        if (!POSITION_REGEX.matcher(moveCommand).matches()) {
            throw new IllegalArgumentException("게임 이동 명령어 입력 형식이 올바르지 않습니다.");
        }
    }

    @Override
    public State execute(GameService gameService, BoardService boardService, Long roomId) {
        if (boardService.isCheckmate(to, roomId)) {
            moveAndPrintBoard(gameService, boardService, roomId);
            ChessGameResult chessGameResult = gameService.generateGameResult(roomId);
            OutputView.printChessGameResult(chessGameResult);
            return State.END;
        }
        moveAndPrintBoard(gameService, boardService, roomId);
        return State.RUNNING;
    }

    private void moveAndPrintBoard(GameService gameService, BoardService boardService, Long roomId) {
        boardService.movePiece(from, to, roomId);
        OutputView.printBoard(boardService.getAllPieces(roomId));
    }

    private Position createPosition(String requestColumn, String requestRow) {
        Column column = ColumnMapper.findByInputValue(requestColumn);
        Row row = RowMapper.findByInputValue(requestRow);
        return new Position(row, column);
    }
}
