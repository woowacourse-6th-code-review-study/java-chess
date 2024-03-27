package chess.domain;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.piece.Score;
import chess.domain.piece.Team;
import chess.domain.position.Position;

public class ChessGame {
    private static final Team INITIAL_TURN = Team.WHITE;
    private static final double PAWN_SCORE_WEIGHT = -0.5;

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

    public Score calculateTeamScore(Team team) {
        Score defaultScore = chessBoard.calcualteDefaultScore(team);
        int sameFilePawnCount = chessBoard.countSameFilePawn(team);
        Score weight = new Score(sameFilePawnCount * PAWN_SCORE_WEIGHT);
        return defaultScore.add(weight);
    }

    public boolean isNotEnd() {
        return chessBoard.isBlackKingAlive() && chessBoard.isWhiteKingAlive();
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
