package domain.game;

import controller.GameController;

public class New implements GameCommand {

    @Override
    public void execute(final GameController gameController) {
        if (gameController.gameStatus() != GameStatus.WAITING) {
            throw new IllegalArgumentException("지금 실행할 수 있는 명령어가 아닙니다.");
        }

        gameController.createNewGame();
        gameController.start();
    }
}
