package domain.game;

import dao.PieceColorDao;
import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceColor;

import java.util.Map;

import static domain.piece.PieceColor.BLACK;
import static domain.piece.PieceColor.WHITE;

public class ChessGame {
    private final PieceColorDao pieceColorDao;
    private final Board board;

    private PieceColor currentColor = WHITE;
    private GameStatus gameStatus = GameStatus.WAITING;

    public ChessGame(final PieceColorDao pieceColorDao, final Board board) {
        this.pieceColorDao = pieceColorDao;
        this.board = board;
    }

    public boolean existPrevGame() {
        return board.existPrevPiecePositionsData();
    }

    public void createChessGame() {
        board.createNewPiecePositions();
        pieceColorDao.delete();
        pieceColorDao.save(currentColor);
    }

    public void roadPrevGame() {
        board.roadPrevPiecePositions();
        currentColor = pieceColorDao.find();
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
        pieceColorDao.update(currentColor);
    }

    public void gameEnd() {
        if (!board.isKingAlive(currentColor) || !board.isKingAlive(currentColor.toggle())) {
            board.clear();
            pieceColorDao.delete();
        }

        gameStatus = GameStatus.END;
    }

    public GameStatus gameStatus() {
        return gameStatus;
    }

    public GameScore getGameResult() {
        double whiteTeamScore = board.calculateTeamScore(WHITE);
        double blackTeamScore = board.calculateTeamScore(BLACK);

        return new GameScore(new Score(whiteTeamScore), new Score(blackTeamScore));
    }

    public PieceColor currentPlayTeamColor() {
        return currentColor;
    }
}
