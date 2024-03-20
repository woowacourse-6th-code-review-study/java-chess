package domain;

import static domain.PieceMoveResult.*;
import static domain.Position.*;
import static domain.Team.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RookTest {
	public static Stream<Arguments> moveFailureParameters() {
		Set<Position> successParameters = moveSuccessParameters().map(Arguments::get)
			.map(objects -> (Position)objects[0])
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
			Arguments.of(G4), Arguments.of(H4)
		);
	}

	public static Stream<Arguments> moveFailureCauseRouteParameters() {
		return Stream.of(
			Arguments.of(D8, D6),
			Arguments.of(D1, D2),
			Arguments.of(H4, F4),
			Arguments.of(A4, B4)
		);
	}

	@ParameterizedTest
	@MethodSource("moveSuccessParameters")
	@DisplayName("룩의 이동 규칙대로 이동이 가능한지 검증")
	void moveSuccess(Position targetPosition) {
		Rook rook = new Rook(D4, WHITE);
		PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of());
		Assertions.assertThat(rook.move(targetPosition, piecesOnChessBoard))
			.isEqualTo(SUCCESS);
	}

	@ParameterizedTest
	@MethodSource("moveFailureParameters")
	@DisplayName("룩의 이동 규칙을 위반한 이동이 불가능한지 검증")
	void moveFailure(Position targetPosition) {
		Rook rook = new Rook(D4, WHITE);
		PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of());
		Assertions.assertThat(rook.move(targetPosition, piecesOnChessBoard))
			.isEqualTo(FAILURE);
	}

	@ParameterizedTest
	@MethodSource("moveFailureCauseRouteParameters")
	@DisplayName("룩의 이동 경로에 다른 말이 있는 경우 이동이 불가능한지 검증")
	void moveFailureCauseRoute(Position targetPosition, Position other) {
		Rook rook = new Rook(D4, WHITE);
		PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of(new Pawn(other, WHITE)));
		Assertions.assertThat(rook.move(targetPosition, piecesOnChessBoard))
			.isEqualTo(FAILURE);
	}

	@ParameterizedTest
	@MethodSource("moveSuccessParameters")
	@DisplayName("룩의 목적지에 같은 팀 말이 있는 경우 이동이 불가능한지 검증")
	void moveFailureCauseTargetIsSameTeam(Position targetPosition) {
		Rook rook = new Rook(D4, WHITE);
		PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of(new Pawn(targetPosition, WHITE)));
		Assertions.assertThat(rook.move(targetPosition, piecesOnChessBoard))
			.isEqualTo(FAILURE);
	}

	@ParameterizedTest
	@MethodSource("moveSuccessParameters")
	@DisplayName("룩의 목적지에 다른 팀 말이 있는 경우 이동이 가능한지 검증")
	void moveSuccessWhenTargetIsOtherTeam(Position targetPosition) {
		Rook rook = new Rook(D4, WHITE);
		PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of(new Pawn(targetPosition, BLACK)));
		Assertions.assertThat(rook.move(targetPosition, piecesOnChessBoard))
			.isEqualTo(CATCH);
	}
}