package chess.view;

import chess.domain.board.position.Position;
import chess.domain.game.Score;
import chess.domain.game.Winner;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.service.dto.ChessGameResult;
import chess.view.mapper.PieceMapper;
import chess.view.mapper.RowMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String EMPTY_BOARD = ". . . . . . . .";

    public static void printRoomNames(List<String> roomNames) {
        System.out.println("체스 게임을 시작합니다.\n");
        System.out.println("현재 저장된 체스 게임 방 목록입니다.");

        if (roomNames.size() == 0) {
            System.out.println("-- 현재 존재하는 체스 게임 방이 없습니다. --");
        }

        for (String name : roomNames) {
            System.out.println("- " + name);
        }

        System.out.println("\n이어서 시작하려면 위에 보이는 방 이름을 입력해주세요.");
        System.out.println("새로 시작하려면 새로운 방 이름을 입력해주세요.");
    }

    public static void printStartMessage(String roomName) {
        System.out.println(roomName + " 체스 방에 입장하였습니다.");
        System.out.println("\n게임 시작 : start");
        System.out.println("게임 종료 : end");
        System.out.println("게임 이동 : move source위치 target위치 - 예. move b2 b3");
        System.out.println("게임 점수 : status");
    }

    public static void printBoard(Map<Position, Piece> board) {
        List<StringBuilder> result = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            result.add(new StringBuilder(EMPTY_BOARD + " | " + (9 - i)));
        }
        result.add(new StringBuilder("ㅡㅡㅡㅡㅡㅡㅡㅡㅡ"));
        result.add(new StringBuilder("a b c d e f g h"));

        board.keySet()
                .forEach(position -> {
                    Piece piece = board.get(position);
                    int rowIndex = RowMapper.findIndexByRow(position.getRow());
                    int columnIndex = position.getColumnIndex();
                    result.get(rowIndex).replace(columnIndex * 2, columnIndex * 2 + 1, PieceMapper.findByPieceType(piece));
                });

        result.forEach(System.out::println);
        System.out.println();
    }

    public static void printChessGameResult(ChessGameResult chessGameResult) {
        System.out.print("왕이 죽어서 게임이 종료되었습니다. \n체스 게임 결과 : ");
        Winner winner = chessGameResult.getWinner();
        Map<Color, Score> teamScore = chessGameResult.getTeamScore();
        Score whiteScore = teamScore.get(Color.WHITE);
        Score blackScore = teamScore.get(Color.BLACK);
        if (winner == Winner.WHITE_WIN) {
            System.out.println("흰색이 검은색 왕을 죽이고 승리하였습니다.");
        }
        if (winner == Winner.BLACK_WIN) {
            System.out.println("검은색이 흰색 왕을 죽이고 승리하였습니다.");
        }
        printScore(whiteScore, blackScore);
    }

    public static void printTeamScore(ChessGameResult chessGameResult) {
        Winner winner = chessGameResult.getWinner();
        Map<Color, Score> teamScore = chessGameResult.getTeamScore();
        Score whiteScore = teamScore.get(Color.WHITE);
        Score blackScore = teamScore.get(Color.BLACK);
        printScore(whiteScore, blackScore);

        if (winner == Winner.WHITE_WIN) {
            System.out.println("현재 대국 상황은 흰색이 우세합니다.");
        }
        if (winner == Winner.BLACK_WIN) {
            System.out.println("현재 대국 상황은 검은색이 우세합니다.");
        }
        if (winner == Winner.DRAW) {
            System.out.println("현재 대국 상황이 호각입니다.");
        }
    }

    private static void printScore(Score whiteScore, Score blackScore) {
        System.out.println("--- 기물 점수 ---");
        System.out.println("흰: " + whiteScore.score());
        System.out.println("검: " + blackScore.score());
    }

    public static void printError(Exception exception) {
        System.out.println(exception.getMessage());
    }
}
