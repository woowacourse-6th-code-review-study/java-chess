package chess.domain;

import chess.domain.piece.PieceType;
import chess.domain.piece.Team;

public class ScoreCalculator {

    private final double blackScore;
    private final double whiteScore;

    public ScoreCalculator(Board board) {
        blackScore = calculateScoreOf(board, Team.BLACK);
        whiteScore = calculateScoreOf(board, Team.WHITE);
    }

    private double calculateScoreOf(Board board, Team team) {
        double basicScore = calculateBasicScoreOf(board, team);
        double minusScore = calculateMinusScoreOf(board, team);

        return basicScore - minusScore;
    }

    private double calculateBasicScoreOf(Board board, Team team) {
        return board.getPiecesOf(team)
                .filter(piece -> piece.isSameTeam(team))
                .mapToDouble(PieceType::scoreOf)
                .sum();
    }

    private double calculateMinusScoreOf(Board board, Team team) {
        int count = (int) board.getPawnPositionsOf(team)
                .filter(position -> board.getPawnPositionsOf(team)
                        .anyMatch(other -> !other.equals(position)
                                && other.isOnSameFile(position)))
                .count();

        return count * 0.5;
    }

    public Team chooseWinner() {
        if (blackScore > whiteScore) {
            return Team.BLACK;
        }
        if (whiteScore > blackScore) {
            return Team.WHITE;
        }
        return null;
    }

    public double getBlackScore() {
        return blackScore;
    }

    public double getWhiteScore() {
        return whiteScore;
    }
}
