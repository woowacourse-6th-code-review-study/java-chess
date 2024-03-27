package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import model.piece.Bishop;
import model.piece.BlackPawn;
import model.piece.King;
import model.piece.Knight;
import model.piece.Piece;
import model.piece.Queen;
import model.piece.Rook;
import model.piece.WhitePawn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PieceScoreTest {

    @DisplayName("해당하는 기물의 점수를 반환한다.")
    @ParameterizedTest
    @MethodSource("getScoreParameterProvider")
    void getScore(final Piece piece, final Score expected) {
        assertThat(PieceScore.getScore(piece)).isEqualTo(expected);
    }

    static Stream<Arguments> getScoreParameterProvider() {
        return Stream.of(
                Arguments.of(new King(Camp.BLACK), new Score(0)),
                Arguments.of(new Queen(Camp.WHITE), new Score(9)),
                Arguments.of(new Rook(Camp.BLACK), new Score(5)),
                Arguments.of(new Bishop(Camp.BLACK), new Score(3)),
                Arguments.of(new Knight(Camp.WHITE), new Score(2.5F)),
                Arguments.of(new WhitePawn(), new Score(1)),
                Arguments.of(new BlackPawn(), new Score(1))
        );
    }
}
