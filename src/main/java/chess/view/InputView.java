package chess.view;

import chess.domain.game.command.Command;
import chess.domain.game.command.EndCommand;
import chess.domain.game.command.MoveCommand;
import chess.domain.game.command.StartCommand;
import chess.domain.game.command.StatusCommand;
import java.util.Scanner;

public class InputView {

    public static final int OPTION_BEGIN_INDEX = 5;
    public static final String SEPARATOR = " ";

    public static Command readStartOrEnd() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!isStartCommand(input) && !isEndCommand(input)) {
            System.out.println("다시 입력해 주세요");
            input = scanner.nextLine();
        }
        if (isEndCommand(input)) {
            return EndCommand.END_COMMAND;
        }
        return StartCommand.START_COMMAND;
    }

    private static boolean isStartCommand(String input) {
        return input.equals("start");
    }

    private static boolean isEndCommand(String input) {
        return input.equals("end");
    }

    public static Command readEndOrMoveOrStatus() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.matches("move [a-h][1-8] [a-h][1-8]") && !isEndCommand(input) && !isStatusCommand(input)) {
            System.out.println("다시 입력해 주세요");
            input = scanner.nextLine();
        }
        if (isEndCommand(input)) {
            return EndCommand.END_COMMAND;
        }
        if (isStatusCommand(input)) {
            return StatusCommand.STATUS_COMMAND;
        }
        String options = input.substring(OPTION_BEGIN_INDEX);
        String[] splitOptions = options.split(SEPARATOR);
        return new MoveCommand(splitOptions);
    }

    private static boolean isStatusCommand(String input) {
        return input.equals("status");
    }
}
