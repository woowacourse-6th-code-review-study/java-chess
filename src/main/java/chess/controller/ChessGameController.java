package chess.controller;

import chess.controller.command.Command;
import chess.controller.command.CommandRouter;
import chess.domain.game.Room;
import chess.service.BoardService;
import chess.service.GameService;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessGameController {

    private final GameService gameService;
    private final BoardService boardService;

    public ChessGameController(GameService gameService, BoardService boardService) {
        this.gameService = gameService;
        this.boardService = boardService;
    }

    public void run() {
        OutputView.printRoomNames(gameService.findAllRoomNames());
        Room room = createRoom();
        OutputView.printStartMessage(room.getName());
        process(gameService, boardService, room.getId());
    }

    private Room createRoom() {
        try {
            String input = InputView.readRoomName();
            return gameService.loadRoom(input);
        } catch (RuntimeException error) {
            OutputView.printError(error);
            return createRoom();
        }
    }

    private void process(GameService gameService, BoardService boardService, Long roomId) {
        State state = State.RUNNING;
        do {
            state = executeCommand(gameService, boardService, state, roomId);
        } while (state != State.END);
    }

    private State executeCommand(GameService gameService, BoardService boardService, State state, Long roomId) {
        try {
            Command command = CommandRouter.findCommendByInput(InputView.readCommend());
            return command.execute(gameService, boardService, roomId);
        } catch (RuntimeException error) {
            OutputView.printError(error);
            return state;
        }
    }
}
