package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.piece.Bishop;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.piece.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreCalculatorTest {

    @Test
    @DisplayName("점수를 계산할 수 있다.")
    void calculateScoreTest() {
        Map<Position, Piece> boardMap = Map.of(
                new Position(File.B, Rank.TWO), new Queen(Team.WHITE),
                new Position(File.F, Rank.TWO), new Rook(Team.WHITE),
                new Position(File.F, Rank.SIX), new Bishop(Team.WHITE),
                new Position(File.D, Rank.THREE), new Knight(Team.BLACK),
                new Position(File.E, Rank.SIX), new Pawn(Team.BLACK),
                new Position(File.A, Rank.FOUR), new King(Team.BLACK));
        Board board = new Board(boardMap);
        ScoreCalculator scoreCalculator = new ScoreCalculator(board);

        assertAll(
                () -> assertThat(scoreCalculator.getWhiteScore()).isEqualTo(17),
                () -> assertThat(scoreCalculator.getBlackScore()).isEqualTo(3.5)
        );
    }

    @Test
    @DisplayName("같은 팀의 폰이 같은 파일에 있을 때 점수를 계산할 수 있다.")
    void calculateScoreTest_whenPawnInSameFile() {
        Map<Position, Piece> boardMap = new java.util.HashMap<>(Map.of(
                new Position(File.B, Rank.EIGHT), new King(Team.BLACK),
                new Position(File.C, Rank.EIGHT), new Rook(Team.BLACK),
                new Position(File.A, Rank.SEVEN), new Pawn(Team.BLACK),
                new Position(File.C, Rank.SEVEN), new Pawn(Team.BLACK),
                new Position(File.D, Rank.SEVEN), new Bishop(Team.BLACK),
                new Position(File.B, Rank.SIX), new Pawn(Team.BLACK),
                new Position(File.E, Rank.SIX), new Queen(Team.BLACK),
                new Position(File.F, Rank.FOUR), new Knight(Team.WHITE),
                new Position(File.G, Rank.FOUR), new Queen(Team.WHITE),
                new Position(File.F, Rank.THREE), new Pawn(Team.WHITE)
        ));
        Map<Position, Piece> addedBoard = Map.of(
                new Position(File.H, Rank.THREE), new Pawn(Team.WHITE),
                new Position(File.F, Rank.TWO), new Pawn(Team.WHITE),
                new Position(File.G, Rank.TWO), new Pawn(Team.WHITE),
                new Position(File.E, Rank.ONE), new Rook(Team.WHITE),
                new Position(File.F, Rank.ONE), new King(Team.WHITE)
        );
        boardMap.putAll(addedBoard);
        Board board = new Board(boardMap);
        ScoreCalculator scoreCalculator = new ScoreCalculator(board);

        assertAll(
                () -> assertThat(scoreCalculator.getWhiteScore()).isEqualTo(19.5),
                () -> assertThat(scoreCalculator.getBlackScore()).isEqualTo(20)
        );
    }
}
