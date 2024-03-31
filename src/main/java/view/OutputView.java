package view;

import domain.board.ChessBoard;
import domain.board.Score;
import dto.RoomDto;

import java.util.List;

public class OutputView {
    private static final MessageResolver messageResolver = new MessageResolver();

    public static void printGameGuideMessage() {
        System.out.println(messageResolver.resolveGameStartMessage());
    }

    public static void printBoard(ChessBoard board) {
        String boardMessage = messageResolver.resolveBoard(board);
        System.out.println(boardMessage);
    }

    public static void printScore(Score score) {
        String scoreMessage = messageResolver.resolveScore(score);
        System.out.println(scoreMessage);
    }

    public static void printWinner(Score score) {
        System.out.println(messageResolver.resolveWinner(score));
    }

    public static void printErrorMessage(Exception e) {
        System.out.println(e.getMessage());
    }

    public static void printGameRoomGuideMessage(List<RoomDto> rooms) {
        System.out.println(messageResolver.resolveRoomList(rooms));
    }

    public static void printEnteringRoomMessage(RoomDto roomDto) {
        System.out.println(messageResolver.resolveEnteringRoomMessage(roomDto));
    }
}
