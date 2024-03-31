package chess.view;

import static chess.view.CommandParser.END;
import static chess.view.CommandParser.MOVE;
import static chess.view.CommandParser.START;
import static chess.view.CommandParser.STATUS;

import chess.dto.BoardDto;
import chess.dto.RankDto;
import chess.dto.ScoreStatusDto;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class OutputView {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String POSITION_EMPTY_MESSAGE = ".";

    public void printStartMessage() {
        System.out.println(resolveStartMessage());
    }

    public void printChessBoardMessage(BoardDto boardSnapshotDto) {
        System.out.println(resolveBoardSnapshotMessage(boardSnapshotDto));
    }

    public void printStatusMessage(ScoreStatusDto scoreStatusDto) {
        System.out.println(resolveStatusMessage(scoreStatusDto));
    }

    private String resolveStartMessage() {
        return new StringJoiner(LINE_SEPARATOR)
                .add("체스 게임을 시작합니다.")
                .add(String.format("> 게임 시작 : %s", START))
                .add(String.format("> 게임 종료 : %s", END))
                .add(String.format("> 점수 출력 : %s", STATUS))
                .add(String.format("> 게임 이동 : %s source위치 target위치 - 예. %s b2 b3", MOVE, MOVE))
                .toString();
    }

    private String resolveBoardSnapshotMessage(BoardDto boardSnapshot) {
        List<RankDto> boardSnapShot = boardSnapshot.getBoardSnapShot();
        return boardSnapShot.stream()
                .map(this::resolveRankSnapshotMessage)
                .collect(Collectors.joining(LINE_SEPARATOR));
    }

    private String resolveRankSnapshotMessage(RankDto rankDto) {
        List<String> rank = rankDto.getRank();
        return String.join("", rank);
    }

    private String resolveStatusMessage(ScoreStatusDto scoreStatusDto) {
        return new StringJoiner(LINE_SEPARATOR)
                .add(resolveTeamStatusMessage("백", scoreStatusDto.getWhiteTeamScore()))
                .add(resolveTeamStatusMessage("흑", scoreStatusDto.getBlackTeamScore()))
                .add(resolveStatusWinnerMessage(scoreStatusDto.getWinnerTeam()))
                .toString();
    }

    private String resolveTeamStatusMessage(String team, double score) {
        return String.format("%s팀: %.1f점", team, score);
    }

    private String resolveStatusWinnerMessage(String winner) {
        return String.format("현 시점 기물 점수 승부 %s 승리", winner);
    }
}
