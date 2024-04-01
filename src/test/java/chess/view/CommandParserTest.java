package chess.view;

import static org.assertj.core.api.Assertions.assertThat;

import chess.controller.command.EndCommand;
import chess.controller.command.MoveCommand;
import chess.controller.command.StartCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommandParserTest {
    private CommandParser commandParser;

    @BeforeEach
    void setUp() {
        commandParser = new CommandParser();
    }

    @DisplayName("시작 입력이 들어오면 시작 명령으로 파싱할 수 있다")
    @Test
    void should_ParseStartCommand_When_InputIsAboutStart() {
        String input = "start";
        assertThat(commandParser.parse(input)).isInstanceOf(StartCommand.class);
    }

    @DisplayName("종료 입력이 들어오면 종료 명령으로 파싱할 수 있다")
    @Test
    void should_ParseEndCommand_When_InputIsAboutEnd() {
        String input = "end";
        assertThat(commandParser.parse(input)).isInstanceOf(EndCommand.class);
    }

    @DisplayName("움직임 입력이 들어오면 움직임 명령으로 파싱할 수 있다")
    @Test
    void should_ParseMoveCommand_When_InputIsAboutMove() {
        String input = "move b2 b4";
        assertThat(commandParser.parse(input)).isInstanceOf(MoveCommand.class);
    }
}
