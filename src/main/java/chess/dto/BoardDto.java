package chess.dto;

import chess.domain.board.ChessBoard;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.view.PieceMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BoardDto {
    private final List<RankDto> boardSnapShot;

    public BoardDto(List<RankDto> boardSnapShot) {
        this.boardSnapShot = boardSnapShot;
    }

    public static BoardDto from(ChessBoard chessBoard) {
        List<RankDto> boardSnapShot = Arrays.stream(Rank.values())
                .map(rank -> rankSnapShot(chessBoard, rank))
                .collect(Collectors.toList());
        return new BoardDto(boardSnapShot);
    }

    private static RankDto rankSnapShot(ChessBoard chessBoard, Rank rank) {
        List<String> rankSnapShot = new ArrayList<>();
        for (File file : File.values()) {
            Position position = new Position(file, rank);
            rankSnapShot.add(squareSnapshot(chessBoard, position));
        }
        return new RankDto(rankSnapShot);
    }

    private static String squareSnapshot(ChessBoard chessBoard, Position position) {
        if (chessBoard.positionIsEmpty(position)) {
            return ".";
        }
        return PieceMessage.messageOf(chessBoard.findPieceByPosition(position));
    }

    public List<RankDto> getBoardSnapShot() {
        return boardSnapShot;
    }
}
