package chess.dto;

import chess.domain.board.ChessBoard;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.view.PieceMessage;

public class BoardSnapShotDto {
    private final String[][] boardSnapShot;

    public BoardSnapShotDto(String[][] boardSnapShot) {
        this.boardSnapShot = boardSnapShot;
    }

    public static BoardSnapShotDto from(ChessBoard chessBoard) {
        String[][] boardSnapShot = new String[Rank.values().length][File.values().length];

        for (Rank rank : Rank.values()) {
            for (File file : File.values()) {
                Position position = new Position(file, rank);
                int rankNumber = position.getRank().getRankNumber();
                int columnNumber = position.getFile().getColumnNumber();
                boardSnapShot[rankNumber][columnNumber] = squareSnapshot(chessBoard, position);
            }
        }
        return new BoardSnapShotDto(boardSnapShot);
    }

    private static String squareSnapshot(ChessBoard chessBoard, Position position) {
        if (chessBoard.positionIsEmpty(position)) {
            return ".";
        }
        return PieceMessage.messageOf(chessBoard.findPieceByPosition(position));
    }

    public String[][] getBoardSnapShot() {
        return boardSnapShot;
    }
}
