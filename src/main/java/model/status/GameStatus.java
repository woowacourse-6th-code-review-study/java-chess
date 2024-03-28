package model.status;

import model.ChessGame;
import model.command.CommandLine;

public interface GameStatus {

    GameStatus play(final CommandLine commandLine, final ChessGame chessGame);

    boolean isRunning();

    boolean isCheck();
}
