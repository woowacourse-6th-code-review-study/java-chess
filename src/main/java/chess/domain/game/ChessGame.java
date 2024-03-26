package chess.domain.game;

import chess.domain.Position;
import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardWrapper;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceMoveResult;
import chess.domain.piece.Team;
import java.util.List;

public class ChessGame {
    private final ChessBoardWrapper chessBoardWrapper;

    public ChessGame() {
        this.chessBoardWrapper = new ChessBoardWrapper(new ChessBoard());
    }

    public PieceMoveResult move(Position from, Position to) {
        return chessBoardWrapper.move(from, to);
    }

    public double calculatePoint(Team team) {
        return chessBoardWrapper.calculatePoint(team);
    }

    public List<Piece> getPiecesOnBoard() {
        return chessBoardWrapper.getPiecesOnBoard();
    }
}