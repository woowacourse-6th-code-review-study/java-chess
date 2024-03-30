package chess.view;

import chess.domain.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.dto.PieceDto;
import chess.dto.PieceType;
import chess.dto.ProgressStatus;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OutputView {

    private static final List<Rank> RANK_ORDER = List.of(
            Rank.EIGHT, Rank.SEVEN, Rank.SIX, Rank.FIVE, Rank.FOUR, Rank.THREE, Rank.TWO, Rank.ONE);
    private static final List<File> FILE_ORDER = List.of(
            File.A, File.B, File.C, File.D, File.E, File.F, File.G, File.H);
    private static final List<Team> TEAM_ORDER = List.of(Team.WHITE, Team.BLACK);
    private static final Map<PieceType, String> PIECE_DISPLAY = Map.of(
            PieceType.KING, "K", PieceType.QUEEN, "Q", PieceType.KNIGHT, "N",
            PieceType.BISHOP, "B", PieceType.ROOK, "R", PieceType.PAWN, "P");
    private static final String EMPTY_SPACE = ".";
    private static final String ERROR_PREFIX = "[ERROR] ";

    public void printStartGame() {
        System.out.println("""
                > 체스 게임을 시작합니다.
                > 게임 시작 : start
                > 게임 종료 : end
                > 게임 이동 : move source위치 target위치 - 예. move b2 b3""");
    }

    public void printBoard(Map<Position, PieceDto> board) {
        System.out.println();
        for (Rank rank : RANK_ORDER) {
            printBoardOneLine(board, rank);
        }
    }

    private void printBoardOneLine(Map<Position, PieceDto> board, Rank rank) {
        for (File file : FILE_ORDER) {
            PieceDto piece = board.get(new Position(file, rank));
            Optional<PieceDto> optional = Optional.ofNullable(piece);
            optional.ifPresentOrElse(this::printPiece, this::printEmptySpace);
        }
        System.out.println();
    }

    private void printPiece(PieceDto piece) {
        if (piece.isBlack()) {
            printBlackPiece(piece.type());
            return;
        }
        printWhitePiece(piece.type());
    }

    private void printEmptySpace() {
        System.out.print(EMPTY_SPACE);
    }

    private void printBlackPiece(PieceType type) {
        String display = PIECE_DISPLAY.get(type);
        System.out.print(display.toUpperCase());
    }

    private void printWhitePiece(PieceType type) {
        String display = PIECE_DISPLAY.get(type);
        System.out.print(display.toLowerCase());
    }

    public void printStatus(Map<Team, Double> status) {
        System.out.println();
        TEAM_ORDER.stream()
                .forEach(team -> printStatus(team, status.get(team)));
    }

    private void printStatus(Team team, double score) {
        if (team.isBlack()) {
            System.out.println("검정 팀 : %.1f".formatted(score));
            return;
        }
        System.out.println("하양 팀 : %.1f".formatted(score));
    }

    public void printWinnerMessage(ProgressStatus status) {
        if (status.isContinue()) {
            throw new IllegalArgumentException("우승자가 결정되지 않았습니다.");
        }

        if (status == ProgressStatus.BLACK_WIN) {
            System.out.println("검정 팀이 승리했습니다.");
            return;
        }
        System.out.println("하양 팀이 승리했습니다.");
    }

    public void printExceptionMessage(Exception exception) {
        System.out.println(ERROR_PREFIX + exception.getMessage());
    }
}
