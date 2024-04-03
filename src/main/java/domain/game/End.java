package domain.game;

import controller.GameController;

public class End implements GameCommand {

    @Override
    public void execute(final GameController gameController) {
        GameStatus gameStatus = gameController.gameStatus();
        if (gameController.gameStatus() == GameStatus.END) {
            throw new IllegalArgumentException("게임이 이미 종료되었습니다.");
        }

        gameController.end();
    }
}
