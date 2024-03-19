package domain;

import static domain.PieceMoveResult.FAILURE;
import static domain.PieceMoveResult.SUCCESS;
import static domain.Position.A1;
import static domain.Position.A4;
import static domain.Position.A7;
import static domain.Position.B2;
import static domain.Position.B4;
import static domain.Position.B6;
import static domain.Position.C3;
import static domain.Position.C4;
import static domain.Position.C5;
import static domain.Position.D1;
import static domain.Position.D2;
import static domain.Position.D3;
import static domain.Position.D4;
import static domain.Position.D5;
import static domain.Position.D6;
import static domain.Position.D7;
import static domain.Position.D8;
import static domain.Position.E3;
import static domain.Position.E4;
import static domain.Position.E5;
import static domain.Position.F2;
import static domain.Position.F4;
import static domain.Position.F6;
import static domain.Position.G1;
import static domain.Position.G4;
import static domain.Position.G7;
import static domain.Position.H4;
import static domain.Position.H8;
import static domain.Team.WHITE;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class QueenTest {
    public static Stream<Arguments> moveFailureParameters() {
        Set<Position> successParameters = moveSuccessParameters().map(Arguments::get)
                .map(objects -> (Position) objects[0])
                .collect(Collectors.toSet());
        return Arrays.stream(Position.values())
                .filter(position -> !successParameters.contains(position))
                .map(Arguments::of);
    }

    public static Stream<Arguments> moveSuccessParameters() {
        return Stream.of(
                Arguments.of(D5), Arguments.of(D6), Arguments.of(D7),
                Arguments.of(D8), Arguments.of(D3), Arguments.of(D2),
                Arguments.of(D1), Arguments.of(A4), Arguments.of(B4),
                Arguments.of(C4), Arguments.of(E4), Arguments.of(F4),
                Arguments.of(G4), Arguments.of(H4),
                Arguments.of(C5), Arguments.of(B6), Arguments.of(A7),
                Arguments.of(E5), Arguments.of(F6), Arguments.of(G7),
                Arguments.of(H8), Arguments.of(C3), Arguments.of(B2),
                Arguments.of(A1), Arguments.of(E3), Arguments.of(F2),
                Arguments.of(G1)
        );
    }

    @ParameterizedTest
    @MethodSource("moveSuccessParameters")
    @DisplayName("퀸의 이동 규칙대로 이동이 가능한지 검증")
    void moveSuccess(Position targetPosition) {
        Queen queen = new Queen(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard() {
        };
        Assertions.assertThat(queen.move(targetPosition, piecesOnChessBoard))
                .isEqualTo(SUCCESS);
    }

    @ParameterizedTest
    @MethodSource("moveFailureParameters")
    @DisplayName("퀸의 이동 규칙을 위반한 이동이 불가능한지 검증")
    void moveFailure(Position targetPosition) {
        Queen queen = new Queen(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard() {
        };
        Assertions.assertThat(queen.move(targetPosition, piecesOnChessBoard))
                .isEqualTo(FAILURE);
    }
}
