package chess.domain;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.Position;

public class ChessGame {
    private static final Team INITIAL_TURN = Team.WHITE;

    private final ChessBoard chessBoard;
    private Team turn;

    public ChessGame(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        turn = INITIAL_TURN;
    }

    public void move(Position start, Position destiantion) {
        validateTurn(chessBoard.findPieceByPosition(start));
        chessBoard.move(start, destiantion);
        turn = turn.otherTeam();
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    private void validateTurn(Piece piece) {
        if (piece.isOtherTeam(turn)) {
            throw new IllegalArgumentException(turn + "의 차례입니다");
        }
    }
}
