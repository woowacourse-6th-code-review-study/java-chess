package model.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CommandTest {

    @DisplayName("올바른 command가 입력되면 예외가 발생하지 않는다.")
    @Test
    void checkCommand() {
        assertAll(
                () -> assertThat(Command.from("start")).isEqualTo(Command.START),
                () -> assertThat(Command.from("end")).isEqualTo(Command.END),
                () -> assertThat(Command.from("move")).isEqualTo(Command.MOVE),
                () -> assertThat(Command.from("a1")).isEqualTo(Command.POSITION),
                () -> assertThat(Command.from("h8")).isEqualTo(Command.POSITION),
                () -> assertThat(Command.from("D8")).isEqualTo(Command.POSITION)
        );
    }

    @DisplayName("각 command 의 body 사이즈를 확인한다.")
    @ParameterizedTest
    @MethodSource("checkBodySizeParameterProvider")
    void checkBodySize(Command command, List<String> body) {
        assertThat(command.isEqualToBodySize(body.size())).isTrue();
    }

    static Stream<Arguments> checkBodySizeParameterProvider() {
        return Stream.of(
                Arguments.of(Command.START, List.of()),
                Arguments.of(Command.END, List.of()),
                Arguments.of(Command.MOVE, List.of("a1", "a2"))
        );
    }
}
