package chess.view;

import chess.domain.board.Board;
import chess.domain.piece.Piece;
import chess.view.mapper.PieceMapper;
import java.util.ArrayList;
import java.util.List;

public class OutputView {

    private static final String EMPTY_BOARD = ". . . . . . . .";

    public void printStartMessage() {
        System.out.println("체스 게임을 시작합니다.");
        System.out.println("게임 시작 : start");
        System.out.println("게임 종료 : end");
        System.out.println("게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public void printBoard(Board board) {
        List<StringBuilder> result = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            result.add(new StringBuilder(EMPTY_BOARD));
        }

        board.getBoard().keySet()
                .forEach(position -> {
                    Piece piece = board.getBoard().get(position);
                    int rowIndex = position.getRowIndex();
                    int columnIndex = position.getColumnIndex();
                    result.get(rowIndex).replace(columnIndex * 2, columnIndex * 2 + 1, PieceMapper.findByPieceType(piece));
                });

        result.forEach(System.out::println);
        System.out.println();
    }

    public void printTeamScore(double whiteTeamScore, double blackTeamScore) {
        System.out.println("---현재 점수---");
        System.out.println("흰색 팀: " + whiteTeamScore);
        System.out.println("검정 팀: " + blackTeamScore);
    }

    public void printError(Exception exception) {
        System.out.println(exception.getMessage());
    }
}
