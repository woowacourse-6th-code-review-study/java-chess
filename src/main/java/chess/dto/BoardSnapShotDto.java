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

public class BoardSnapShotDto {
    private final List<RankSnapShotDto> boardSnapShot;

    public BoardSnapShotDto(List<RankSnapShotDto> boardSnapShot) {
        this.boardSnapShot = boardSnapShot;
    }

    public static BoardSnapShotDto from(ChessBoard chessBoard) {
        List<RankSnapShotDto> boardSnapShot = Arrays.stream(Rank.values())
                .map(rank -> rankSnapShot(chessBoard, rank))
                .collect(Collectors.toList());
        return new BoardSnapShotDto(boardSnapShot);
    }

    private static RankSnapShotDto rankSnapShot(ChessBoard chessBoard, Rank rank) {
        List<String> rankSnapShot = new ArrayList<>();
        for (File file : File.values()) {
            Position position = new Position(file, rank);
            rankSnapShot.add(squareSnapshot(chessBoard, position));
        }
        return new RankSnapShotDto(rankSnapShot);
    }

    private static String squareSnapshot(ChessBoard chessBoard, Position position) {
        if (chessBoard.positionIsEmpty(position)) {
            return ".";
        }
        return PieceMessage.messageOf(chessBoard.findPieceByPosition(position));
    }

    public List<RankSnapShotDto> getBoardSnapShot() {
        return boardSnapShot;
    }
}
