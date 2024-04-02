package view;

import domain.board.ChessBoard;
import domain.board.Score;
import dto.RoomDto;
import dto.UserDto;

import java.util.List;

public class OutputView {
    public static final String LINE_SEPARATOR = System.lineSeparator();
    private static final MessageResolver messageResolver = new MessageResolver();

    public static void printGameGuideMessage() {
        printWithLineSeparator(messageResolver.resolveGameStartMessage());
    }

    public static void printBoard(ChessBoard board) {
        String boardMessage = messageResolver.resolveBoardMessage(board);
        printWithLineSeparator(boardMessage);
    }

    public static void printScore(Score score) {
        String scoreMessage = messageResolver.resolveScoreMessage(score);
        printWithLineSeparator(scoreMessage);
    }

    public static void printWinner(Score score) {
        printWithLineSeparator(messageResolver.resolveWinnerMessage(score));
    }

    public static void printErrorMessage(Exception e) {
        printWithLineSeparator(e.getMessage());
    }

    public static void printGameRoomGuideMessage(List<RoomDto> rooms) {
        printWithLineSeparator(messageResolver.resolveRoomGuideMessage(rooms));
    }

    public static void printEnteringRoomMessage(RoomDto roomDto) {
        printWithLineSeparator(messageResolver.resolveEnteringRoomMessage(roomDto));
    }

    public static void printUserNameInputMessage() {
        printWithLineSeparator(messageResolver.resolveUserNameInputMessage());
    }

    public static void printUserNameMessage(UserDto user) {
        printWithLineSeparator(messageResolver.resolveUserNameMessage(user));
    }

    private static void printWithLineSeparator(String message) {
        System.out.println(LINE_SEPARATOR + message);
    }
}
