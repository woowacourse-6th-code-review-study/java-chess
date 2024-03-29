package domain.game;

import controller.GameController;

public class Start implements GameCommand {

    @Override
    public void execute(final GameController gameController) {
        if (gameController.gameStatus() != GameStatus.WAITING) {
            throw new IllegalArgumentException("이미 게임이 진행중입니다.");
        }

        gameController.buildGame();
    }
}
