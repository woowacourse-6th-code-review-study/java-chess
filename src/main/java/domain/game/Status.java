package domain.game;

import controller.GameController;

public class Status implements GameCommand {

    @Override
    public void execute(final GameController gameController) {
        if (gameController.gameStatus() != GameStatus.RUNNING) {
            throw new IllegalArgumentException("status 명령어는 게임 중에만 실행할 수 있습니다.");
        }

        gameController.printGameStatus();
    }
}
