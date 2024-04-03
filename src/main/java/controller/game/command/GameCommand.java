package controller.game.command;

import domain.ChessGame;

public interface GameCommand {
    void execute(ChessGame game);
}
