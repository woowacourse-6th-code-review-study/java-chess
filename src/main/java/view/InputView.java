package view;

import controller.command.Command;
import view.command.CommandInput;
import view.command.CommandType;

import java.util.Scanner;

public class InputView {
    public static final String WRONG_COMMAND_ERROR_MESSAGE = "잘못된 형식의 명령어입니다.";

    private static final Scanner SCANNER = new Scanner(System.in);

    public static Command readCommand() {
        try {
            CommandInput input = readCommandInput();
            return CommandType.getCommand(input);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(WRONG_COMMAND_ERROR_MESSAGE);
        }
    }

    private static CommandInput readCommandInput() {
        return new CommandInput(SCANNER.nextLine());
    }
}
