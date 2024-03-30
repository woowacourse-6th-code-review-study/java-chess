package domain;

import domain.board.ChessBoard;
import domain.board.Score;
import domain.piece.Color;
import domain.position.Position;
import domain.state.ReadyState;
import domain.state.State;

public class ChessGame {
    private final ChessBoard board;
    private State state;

    public ChessGame(final ChessBoard board) {
        this(board, new ReadyState());
    }

    private ChessGame(final ChessBoard board, final State state) {
        this.board = board;
        this.state = state;
    }

    public void start() {
        this.state = state.start();
    }

    public void move(final Position source, final Position target) {
        this.state = state.move();
        board.move(source, target);
        if (board.isKingNotExist()) {
            this.state = state.end();
        }
    }

    public void end() {
        state = state.end();
    }

    public boolean isPlaying() {
        return state.isPlaying();
    }

    public boolean isGameOver() {
        return board.isKingNotExist();
    }

    public ChessBoard getBoard() {
        return board;
    }

    public Color getTurn() {
        return board.getTurn();
    }

    public Score getScore() {
        return board.calculateScore();
    }
}
