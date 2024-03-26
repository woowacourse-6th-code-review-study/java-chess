package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardAdaptor;
import chess.domain.game.command.MoveCommand;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceMoveResult;
import chess.domain.piece.Team;
import java.util.List;

public class ChessGame {
    private final ChessBoardForChessGame chessBoard;

    public ChessGame() {
        this.chessBoard = new ChessBoardAdaptor(new ChessBoard());
    }

    public PieceMoveResult move(MoveCommand moveCommand) {
        return chessBoard.move(moveCommand);
    }

    public double calculatePoint(Team team) {
        return chessBoard.calculateTeamScore(team);
    }

    public List<Piece> getPiecesOnBoard() {
        return chessBoard.getPiecesOnBoard();
    }
}
