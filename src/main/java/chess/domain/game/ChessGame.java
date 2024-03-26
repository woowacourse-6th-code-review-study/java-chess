package chess.domain.game;

import chess.domain.Position;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceMoveResult;
import chess.domain.piece.Team;
import java.util.List;

public class ChessGame {
    private final ChessBoard chessBoard;

    public ChessGame() {
        this.chessBoard = new ChessBoard();
    }

    public PieceMoveResult move(Position from, Position to) {
        return chessBoard.move(from, to);
    }

    public double calculatePoint(Team team) {
        return chessBoard.calculatePoint(team);
    }

    public List<Piece> getPiecesOnBoard() {
        return chessBoard.getPiecesOnBoard();
    }
}
