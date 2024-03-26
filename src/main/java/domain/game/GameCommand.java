package domain.game;

import controller.GameController;

public interface GameCommand {
    void execute(GameController gameController);
}
