package chess.view;

import java.util.Scanner;

public class InputView {
    public static final Scanner scanner = new Scanner(System.in);

    //TODO: 게임 커맨드 도메인을 반환하도록 할 지 고민해보기
    //TODO: 메시지 분리하기
    public String readGameCommand() {
        System.out.println(
                "> 게임 시작 : start\n" + "> 게임 종료 : end\n" + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3\n");
        return scanner.nextLine();
    }
}