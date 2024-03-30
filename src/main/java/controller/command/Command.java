package controller.command;

import domain.ChessGame;
import view.OutputView;

public interface Command {
    void execute(ChessGame game, OutputView outputView);
}
