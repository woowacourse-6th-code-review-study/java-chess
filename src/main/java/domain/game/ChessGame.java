package domain.game;

import dao.TurnColorDao;
import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceColor;

import java.util.Map;

import static domain.piece.PieceColor.BLACK;
import static domain.piece.PieceColor.WHITE;

public class ChessGame {
    private final TurnColorDao turnColorDao;
    private final Board board;

    private PieceColor currentColor = WHITE;
    private GameStatus gameStatus = GameStatus.WAITING;

    public ChessGame(final TurnColorDao turnColorDao, final Board board) {
        this.turnColorDao = turnColorDao;
        this.board = board;
    }

    public boolean existPrevGame() {
        return board.existPrevPiecePositionsData();
    }

    public void createChessGame() {
        board.createNewPiecePositions();
        turnColorDao.delete();
        turnColorDao.save(currentColor);
    }

    public void roadPrevGame() {
        board.roadPrevPiecePositions();
        currentColor = turnColorDao.find();
    }

    public void gameStart() {
        gameStatus = GameStatus.RUNNING;
    }

    public boolean isRunning() {
        return gameStatus == GameStatus.RUNNING;
    }

    public Map<Position, Piece> piecePositions() {
        return board.piecePositions();
    }

    public void movePiece(final Position source, final Position destination) {
        board.movePiece(currentColor, source, destination);
        if (!board.isKingAlive(currentColor.toggle())) {
            gameEnd();
            return;
        }

        currentColor = currentColor.toggle();
        turnColorDao.update(currentColor);
    }

    public void gameEnd() {
        if (!board.isKingAlive(currentColor) || !board.isKingAlive(currentColor.toggle())) {
            board.clear();
            turnColorDao.delete();
        }

        gameStatus = GameStatus.END;
    }

    public GameStatus gameStatus() {
        return gameStatus;
    }

    public GameScore getGameResult() {
        Score whiteTeamScore = Score.of(board, WHITE);
        Score blackTeamScore = Score.of(board, BLACK);

        return new GameScore(whiteTeamScore, blackTeamScore);
    }

    public PieceColor currentPlayTeamColor() {
        return currentColor;
    }
}
