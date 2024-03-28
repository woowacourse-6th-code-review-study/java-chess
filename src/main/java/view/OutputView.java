package view;

import domain.game.GameResult;
import domain.game.Score;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import dto.BoardDto;
import dto.PieceDto;
import dto.PositionDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static domain.piece.PieceType.PAWN;
import static domain.piece.PieceType.ROOK;
import static domain.piece.PieceType.KNIGHT;
import static domain.piece.PieceType.BISHOP;
import static domain.piece.PieceType.QUEEN;
import static domain.piece.PieceType.KING;

public class OutputView {
    private static final Map<PieceType, String> pieceFormat = Map.ofEntries(
            Map.entry(ROOK, "R"),
            Map.entry(KNIGHT, "N"),
            Map.entry(BISHOP, "B"),
            Map.entry(QUEEN, "Q"),
            Map.entry(KING, "K"),
            Map.entry(PAWN, "P")
    );

    public void printErrorMessage(final String message) {
        System.out.println(message);
        System.out.println();
    }

    public void printWelcomeMessage() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.println("> 게임 시작: start");
        System.out.println("> 게임 종료: end");
        System.out.println("> 게임 이동: move source위치 target위치 - 예. move b2 b3");
        System.out.println();
    }

    public void printTurnStatus(final BoardDto boardDto, final PieceColor pieceColor) {
        System.out.println("====Status=====");
        System.out.println(pieceColor.name() + "팀 차례입니다.");
        System.out.println("===============");
        List<String> boardStatus = convertBoardStatus(boardDto.value());
        boardStatus.forEach(System.out::println);
        System.out.println();
    }

    private List<String> convertBoardStatus(final Map<PositionDto, PieceDto> boardStatus) {
        String[][] boardMessage = initEmptyBoardMessage();
        boardStatus.forEach((position, piece) -> addPieceStatusInBoardMessage(boardMessage, position, piece));

        return Arrays.stream(boardMessage)
                .map(rowString -> String.join("", rowString))
                .toList();
    }

    private static String[][] initEmptyBoardMessage() {
        String[][] strings = new String[8][8];
        for (String[] row : strings) {
            Arrays.fill(row, ".");
        }

        return strings;
    }

    private void addPieceStatusInBoardMessage(final String[][] boardMessage, final PositionDto position, final PieceDto piece) {
        boardMessage[position.row()][position.column()] = convertPieceTypeToString(piece.type(), piece.color());
    }

    private String convertPieceTypeToString(final PieceType pieceType, final PieceColor pieceColor) {
        String pieceMessage = pieceFormat.get(pieceType);

        if (pieceColor == PieceColor.WHITE) {
            return pieceMessage.toLowerCase();
        }

        return pieceMessage;
    }

    public void printGameResult(final Score whiteTeamScore, final Score blackTeamScore, final GameResult gameResult) {
        System.out.println("White Score : " + whiteTeamScore.value());
        System.out.println("Black Score : " + blackTeamScore.value());
        System.out.println(convertToGameResultMessage(gameResult));
        System.out.println();
    }

    private String convertToGameResultMessage(final GameResult gameResult) {
        if (gameResult == GameResult.BLACK_WIN) {
            return "블랙이 이기고 있습니다!";
        }

        if (gameResult == GameResult.WHITE_WIN) {
            return "화이트가 이기고 있습니다!";
        }

        return "막상막하입니다!";
    }
}
