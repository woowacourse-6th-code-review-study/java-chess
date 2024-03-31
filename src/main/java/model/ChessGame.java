package model;

import java.util.Map;
import model.piece.Piece;
import model.position.Moving;
import model.position.Position;

public class ChessGame {

    private final Board board;
    private final GameTurn gameTurn;

    public ChessGame(final Board board, final GameTurn gameTurn) {
        this.board = board;
        this.gameTurn = gameTurn;
    }

    public static ChessGame setupStartingPosition() {
        return new ChessGame(Board.create(), GameTurn.create());
    }

    public void move(final Moving moving) {
        board.validate(moving, getCamp());
        board.move(moving);
        gameTurn.progress();
    }

    public Map<Position, Piece> getBoard() {
        return board.getPieces();
    }

    public Camp getCamp() {
        return gameTurn.getCamp();
    }

    public Turn getTurn() {
        return gameTurn.getTurn();
    }

    public Score calculateScore(final Camp camp) {
        return board.calculateScore(camp);
    }
}
