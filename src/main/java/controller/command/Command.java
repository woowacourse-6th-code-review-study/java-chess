package controller.command;

import domain.ChessGame;

public interface Command {
    void execute(ChessGame game);
}
