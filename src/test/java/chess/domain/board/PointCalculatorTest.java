package chess.domain.board;

import static chess.domain.Position.A2;
import static chess.domain.Position.A3;
import static chess.domain.Position.A4;
import static chess.domain.Position.A5;
import static chess.domain.Position.A6;
import static chess.domain.Position.A8;
import static chess.domain.Position.B2;
import static chess.domain.Position.H1;
import static chess.domain.piece.Team.BLACK;
import static chess.domain.piece.Team.WHITE;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.piece.King;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PointCalculatorTest {
    @Test
    @DisplayName("각 팀의 점수가 잘 계산되는지 검증")
    void calculatePoint() {
        ChessBoard chessBoard = new ChessBoard();
        List<Piece> piecesOnBoard = chessBoard.getPiecesOnBoard();

        double whiteTeamPoint = PointCalculator.calculatePoint(WHITE, piecesOnBoard);
        double blackTeamPoint = PointCalculator.calculatePoint(WHITE, piecesOnBoard);
        Offset<Double> offset = Offset.offset(0.01);

        assertAll(
                () -> Assertions.assertThat(whiteTeamPoint).isCloseTo(38.0, offset),
                () -> Assertions.assertThat(blackTeamPoint).isCloseTo(38.0, offset)
        );
    }

    @Test
    @DisplayName("같은 열의 폰이 포함된 각 팀의 점수가 잘 계산되는지 검증")
    void calculatePointWithPawn() {
        ChessBoard chessBoard = new ChessBoard(new King(A8, WHITE), new King(H1, BLACK), new Pawn(A2, BLACK),
                new Pawn(B2, BLACK), new Pawn(A3, BLACK),
                new Pawn(A4, WHITE), new Pawn(A5, WHITE), new Pawn(A6, WHITE));
        List<Piece> piecesOnBoard = chessBoard.getPiecesOnBoard();

        double whiteTeamPoint = PointCalculator.calculatePoint(WHITE, piecesOnBoard);
        double blackTeamPoint = PointCalculator.calculatePoint(BLACK, piecesOnBoard);
        Offset<Double> offset = Offset.offset(0.01);

        assertAll(
                () -> Assertions.assertThat(blackTeamPoint).isCloseTo(2.0, offset),
                () -> Assertions.assertThat(whiteTeamPoint).isCloseTo(1.5, offset)
        );
    }
}
