package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardAdaptor;
import chess.domain.game.command.MoveCommand;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceMoveResult;
import chess.domain.piece.Team;
import java.util.List;
import java.util.Map;

public class ChessGame {
    private final ChessBoardForChessGame chessBoard;

    public ChessGame(List<Piece> pieces, Team currentTeam) {
        this.chessBoard = new ChessBoardAdaptor(new ChessBoard(pieces, currentTeam));
    }

    public ChessGame() {
        this.chessBoard = new ChessBoardAdaptor(new ChessBoard());
    }

    public PieceMoveResult move(MoveCommand moveCommand) {
        return chessBoard.move(moveCommand);
    }

    public Map<Team, Double> calculateScores() {
        return chessBoard.calculateScores();
    }

    public List<Piece> getPiecesOnBoard() {
        return chessBoard.getPiecesOnBoard();
    }

    public Team currentTeam() {
        return chessBoard.getCurrentTeam();
    }
}
