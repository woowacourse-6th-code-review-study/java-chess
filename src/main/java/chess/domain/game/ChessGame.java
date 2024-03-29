package chess.domain.game;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.board.position.Direction;
import chess.domain.board.position.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ChessGame {

    private static final Color START_TURN = Color.WHITE;

    private final Board board;
    private Color currentTurn;

    public ChessGame() {
        this.board = new Board(new BoardFactory().initialize());
        currentTurn = START_TURN;
    }

    public ChessGameResult generateGameResult(Position to) {
        Map<Color, Score> teamScore = board.calculateScore();
        if (board.isCheckmate(to)) {
            Piece king = board.findPieceByPosition(to);
            return new ChessGameResult(Winner.selectWinnerByCheckmate(king.getColor()), teamScore);
        }
        return new ChessGameResult(Winner.selectWinnerByScore(teamScore), teamScore);
    }

    public Map<Color, Score> calculateScore() {
        return board.calculateScore();
    }

    public void movePiece(Position from, Position to) {
        validateUserTurn(from);
        List<Position> movablePositions = generateMovablePositions(from);
        if (movablePositions.contains(to)) {
            board.movePiece(from, to);
            currentTurn = currentTurn.change();
            return;
        }
        throw new IllegalArgumentException("기물을 해당 위치로 이동시킬 수 없습니다.");
    }

    public boolean isCheckmate(Position to) {
        return board.isCheckmate(to);
    }

    private void validateUserTurn(Position from) {
        Piece piece = board.findPieceByPosition(from);
        if (piece.isNotSameColor(currentTurn)) {
            throw new IllegalArgumentException("상대방의 기물을 움직일 수 없습니다. 현재 턴 : " + currentTurn);
        }
    }

    private List<Position> generateMovablePositions(Position position) {
        Piece piece = board.findPieceByPosition(position);
        Map<Direction, Queue<Position>> candidateAllPositions = piece.generateAllDirectionPositions(position);
        return new PositionsFilter().generateValidPositions(candidateAllPositions, piece, board);
    }

    public Board getBoard() {
        return board;
    }
}
