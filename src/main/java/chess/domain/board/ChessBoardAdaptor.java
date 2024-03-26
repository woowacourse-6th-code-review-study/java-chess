package chess.domain.board;

import static chess.domain.piece.Team.BLACK;
import static chess.domain.piece.Team.WHITE;

import chess.domain.Position;
import chess.domain.game.ChessBoardForChessGame;
import chess.domain.game.command.MoveCommand;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceMoveResult;
import chess.domain.piece.Team;
import java.util.List;
import java.util.Map;

public class ChessBoardAdaptor implements ChessBoardForChessGame {
    private final ChessBoard chessBoard;

    public ChessBoardAdaptor(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    @Override
    public PieceMoveResult move(MoveCommand moveCommand) {
        List<Position> positions = moveCommand.getOptions();
        Position from = positions.get(0);
        Position to = positions.get(1);
        return chessBoard.move(from, to);
    }

    @Override
    public List<Piece> getPiecesOnBoard() {
        return chessBoard.getPiecesOnBoard();
    }

    @Override
    public Map<Team, Double> calculateScores() {
        double blackTeamScore = chessBoard.calculatePoint(BLACK);
        double whiteTeamScore = chessBoard.calculatePoint(WHITE);
        return Map.of(BLACK, blackTeamScore, WHITE, whiteTeamScore);
    }
}
