package chess.view;

import chess.domain.piece.PieceMoveResult;
import chess.domain.piece.PieceType;
import chess.domain.piece.Team;
import chess.dto.PieceDTO;
import java.util.ArrayList;
import java.util.List;

public class OutputView {
    public static void printGuide() {
        System.out.printf("> 체스 게임을 시작합니다.%n"
                + "> 게임 시작 : start%n"
                + "> 게임 종료 : end%n"
                + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3%n"
                + "> 현재 점수 확인 : status%n");
    }

    public static void printChessBoard(List<PieceDTO> pieceDTOS) {
        List<List<Character>> boardStatus = new ArrayList<>(8);
        for (int i = 0; i < 9; i++) {
            boardStatus.add(new ArrayList<>(List.of(' ', '.', '.', '.', '.', '.', '.', '.', '.')));
        }

        for (PieceDTO pieceDTO : pieceDTOS) {
            int column = pieceDTO.getColumn();
            int row = pieceDTO.getRow();
            boardStatus.get(9 - row).set(column, mappingPiece(pieceDTO).value);
        }

        for (int i = 1; i < boardStatus.size(); i++) {
            for (Character c : boardStatus.get(i)) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private static PieceAsset mappingPiece(PieceDTO pieceDTO) {
        Team pieceTeam = pieceDTO.getTeam();
        PieceType pieceType = pieceDTO.getPieceType();
        return PieceAsset.valueOf(pieceTeam.name() + "_" + pieceType.name());
    }

    public static void printReInputGuide() {
        System.out.println("다시 입력해 주세요");
    }

    public static void printStatus(Team team, double point) {
        System.out.printf("%s: %f%n", team.name(), point);
    }

    public static void printWinner(PieceMoveResult pieceMoveResult) {
        if (pieceMoveResult.equals(PieceMoveResult.BLACK_WIN)) {
            System.out.println("BLACK 승리");
            return;
        }
        if (pieceMoveResult.equals(PieceMoveResult.WHITE_WIN)) {
            System.out.println("WHITE 승리");
        }
    }

    public static void currentWinner(double whitePoint, double blackPoint) {
        if (blackPoint > whitePoint) {
            System.out.println("BLACK이 이기는 중");
            return;
        }
        if (whitePoint > blackPoint) {
            System.out.println("WHITE가 이기는 중");
            return;
        }
        System.out.println("비기는 중");
    }

    enum PieceAsset {
        BLACK_KING('K'),
        BLACK_QUEEN('Q'),
        BLACK_ROOK('R'),
        BLACK_BISHOP('B'),
        BLACK_KNIGHT('N'),
        BLACK_PAWN('P'),
        WHITE_KING('k'),
        WHITE_QUEEN('q'),
        WHITE_ROOK('r'),
        WHITE_BISHOP('b'),
        WHITE_KNIGHT('n'),
        WHITE_PAWN('p');

        private final char value;

        PieceAsset(char value) {
            this.value = value;
        }
    }
}
