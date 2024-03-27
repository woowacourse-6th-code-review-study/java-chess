package view;

import controller.command.Command;
import view.command.CommandInput;
import view.command.CommandType;

import java.util.Scanner;

public class InputView {
    public static final String WRONG_COMMAND_ERROR_MESSAGE = "잘못된 형식의 명령어입니다.";
    public static final String GAME_NOT_STARTED_ERROR_MESSAGE = "먼저, 게임을 시작해 주세요.";

    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

//    public Command readStartCommand() {
//        return CommandType.getCommand(readCommandInput());
//    }

    public Command readCommand() {
        try {
            CommandInput input = readCommandInput();
            return CommandType.getCommand(input);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(WRONG_COMMAND_ERROR_MESSAGE);
        }
    }

    private CommandInput readCommandInput() {
        return new CommandInput(scanner.nextLine());
    }
}
