package controller.game.command;

import domain.ChessGame;

public interface Command {
    void execute(ChessGame game);
}
