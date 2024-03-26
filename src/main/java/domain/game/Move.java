package domain.game;

import controller.GameController;
import domain.board.Position;

public class Move implements GameCommand {
    private final Position source;
    private final Position destination;

    public Move(final Position source, final Position destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public void execute(final GameController gameController) {
        if (gameController.gameStatus() != GameStatus.RUNNING) {
            throw new IllegalArgumentException("아직 게임이 시작되지 않았습니다.");
        }

        gameController.movePiece(source, destination);
    }
}
