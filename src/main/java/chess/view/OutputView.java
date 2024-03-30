package chess.view;

import chess.domain.board.Board;
import chess.domain.board.position.Row;
import chess.domain.game.ChessGameResult;
import chess.domain.game.Score;
import chess.domain.game.Winner;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.view.mapper.PieceMapper;
import chess.view.mapper.RowMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String EMPTY_BOARD = ". . . . . . . .";

    public static void printStartMessage() {
        System.out.println("체스 게임을 시작합니다.");
        System.out.println("게임 시작 : start");
        System.out.println("게임 종료 : end");
        System.out.println("게임 이동 : move source위치 target위치 - 예. move b2 b3");
        System.out.println("게임 점수 : status");
    }

    public static void printBoard(Board board) {
        List<StringBuilder> result = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            result.add(new StringBuilder(EMPTY_BOARD + " | " + RowMapper.findByRow(Row.findByIndex(i))));
        }
        result.add(new StringBuilder("ㅡㅡㅡㅡㅡㅡㅡㅡㅡ"));
        result.add(new StringBuilder("a b c d e f g h"));

        board.getBoard().keySet()
                .forEach(position -> {
                    Piece piece = board.getBoard().get(position);
//                    int rowIndex = position.getRowIndex();
                    int rowIndex = RowMapper.findByIndex(position.getRow());
                    int columnIndex = position.getColumnIndex();
                    result.get(rowIndex).replace(columnIndex * 2, columnIndex * 2 + 1, PieceMapper.findByPieceType(piece));
                });

        result.forEach(System.out::println);
        System.out.println();
    }

    public static void printChessGameResult(ChessGameResult chessGameResult) {
        StringBuilder stringBuilder = new StringBuilder("왕이 잡혀서 게임이 종료되었습니다. \n체스 게임 결과 : ");
        Winner winner = chessGameResult.getWinner();
        Map<Color, Score> teamScore = chessGameResult.getTeamScore();
        if (winner == Winner.WHITE_WIN) {
            stringBuilder.append("흰색 승리!");
        }
        if (winner == Winner.BLACK_WIN) {
            stringBuilder.append("검정 승리!");
        }
        if (winner == Winner.DRAW) {
            stringBuilder.append("무승부!");
        }

        System.out.println(stringBuilder);
        printTeamScore(teamScore.get(Color.WHITE), teamScore.get(Color.BLACK));
    }

    public static void printTeamScore(Score whiteTeamScore, Score blackTeamScore) {
        System.out.println("--- 기물 점수 ---");
        System.out.println("흰색: " + whiteTeamScore.score());
        System.out.println("검정: " + blackTeamScore.score());
    }

    public static void printError(Exception exception) {
        System.out.println(exception.getMessage());
    }
}
