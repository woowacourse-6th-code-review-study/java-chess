package view;

import view.command.CommandInput;
import view.command.GameCommandType;
import view.command.RoomCommandType;

import java.util.Scanner;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String WRONG_COMMAND_ERROR_MESSAGE = "잘못된 형식의 명령어입니다.";

    public static controller.game.command.Command readGameCommand() {
        try {
            CommandInput input = readCommandInput();
            return GameCommandType.getCommand(input);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(WRONG_COMMAND_ERROR_MESSAGE);
        }
    }

    public static controller.room.command.Command readRoomCommand() {
        try {
            CommandInput input = readCommandInput();
            return RoomCommandType.getCommand(input);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(WRONG_COMMAND_ERROR_MESSAGE);
        }
    }

    private static CommandInput readCommandInput() {
        return new CommandInput(SCANNER.nextLine());
    }
}
