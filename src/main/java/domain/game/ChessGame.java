package domain.game;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceColor;

import java.util.Map;

import static domain.piece.PieceColor.WHITE;

public class ChessGame {
    private final Board board;
    private final GameStatus gameStatus;

    private PieceColor currentColor = WHITE;
    private boolean isGameRunning = false;

    public ChessGame(final Board board, final GameStatus gameStatus) {
        this.board = board;
        this.gameStatus = gameStatus;
    }

    public static ChessGame initGame() {
        return new ChessGame(BoardInitializer.initBoard(), GameStatus.WAITING);
    }

    public void gameStart() {
        isGameRunning = true;
    }

    public void gameEnd() {
        isGameRunning = false;
    }

    public boolean isWaiting() {
        return gameStatus == GameStatus.WAITING;
    }

    public boolean isRunning() {
        return gameStatus == GameStatus.RUNNING;
    }

    public boolean isEnd() {
        return gameStatus == GameStatus.END;
    }

    public Map<Position, Piece> piecePositions() {
        return board.piecePositions();
    }

    public void movePiece(final Position source, final Position destination) {
        board.movePiece(currentColor, source, destination);
        currentColor = currentColor.toggle();
    }
}
