package chess.domain.game;

import chess.domain.game.command.MoveCommand;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceMoveResult;
import chess.domain.piece.Team;
import java.util.List;
import java.util.Map;

public interface ChessBoardForChessGame {
    PieceMoveResult move(MoveCommand moveCommand);

    List<Piece> getPiecesOnBoard();

    Map<Team, Double> calculateScores();

    Team getCurrentTeam();
}
