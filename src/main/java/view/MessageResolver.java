package view;

import domain.board.ChessBoard;
import domain.board.Score;
import domain.piece.Piece;
import domain.piece.Type;
import domain.position.File;
import domain.position.Position;
import domain.position.Rank;
import dto.RoomDto;
import dto.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static view.command.GameCommandType.END;
import static view.command.GameCommandType.MOVE;
import static view.command.GameCommandType.START;
import static view.command.RoomCommandType.NEW_ROOM;
import static view.command.RoomCommandType.ROOM_SELECTION;
import static view.command.UserCommandType.FIND_USER;

public class MessageResolver {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final int BOARD_LENGTH = 8;
    private static final Map<Type, String> PIECE_DISPLAY = Map.of(
            Type.PAWN, "p",
            Type.KNIGHT, "n",
            Type.BISHOP, "b",
            Type.ROOK, "r",
            Type.QUEEN, "q",
            Type.KING, "k"
    );

    public String resolveGameStartMessage() {
        String gameStartMessage = "> 체스 게임을 시작합니다.";
        String gameStartCommandMessage = String.format("> 게임 시작 : %s", START.message());
        String gameEndCommandMessage = String.format("> 게임 종료 : %s", END.message());
        String gameMoveCommandMessage = String.format("> 게임 이동 : %s source위치 target위치 - 예. %s b2 b3",
                MOVE.message(), MOVE.message());
        return String.join(LINE_SEPARATOR, gameStartMessage, gameStartCommandMessage,
                gameEndCommandMessage, gameMoveCommandMessage);
    }

    public String resolveBoardMessage(ChessBoard board) {
        List<String> boardMessage = IntStream.rangeClosed(1, 8)
                .mapToObj(rank -> resolveOneRank(board, Rank.fromNumber(rank)))
                .collect(Collectors.toList());
        Collections.reverse(boardMessage);
        return String.join(LINE_SEPARATOR, boardMessage);
    }

    private String resolveOneRank(ChessBoard board, Rank targetRank) {
        StringBuilder rankMessage = new StringBuilder(".".repeat(BOARD_LENGTH));
        board.getBoard().entrySet().stream()
                .filter(entry -> resolvePosition(entry).rank() == targetRank)
                .forEach(positionPieceEntry -> updateRankMessage(rankMessage, positionPieceEntry));
        return rankMessage.toString();
    }

    private void updateRankMessage(StringBuilder rankMessage, Map.Entry<Position, Piece> positionPieceEntry) {
        Position position = resolvePosition(positionPieceEntry);
        Piece piece = resolvePiece(positionPieceEntry);
        File file = position.file();
        rankMessage.setCharAt(file.order(), pieceDisplay(piece).charAt(0));
    }

    private Piece resolvePiece(Map.Entry<Position, Piece> positionAndPiece) {
        return positionAndPiece.getValue();
    }

    private Position resolvePosition(Map.Entry<Position, Piece> positionAndPiece) {
        return positionAndPiece.getKey();
    }

    private String pieceDisplay(Piece piece) {
        String pieceName = PIECE_DISPLAY.get(piece.type());
        if (piece.color().isBlack()) {
            return pieceName.toUpperCase();
        }
        return pieceName;
    }

    public String resolveScoreMessage(Score score) {
        String whiteScoreMessage = String.format("WHITE 점수: %.1f", score.getWhiteScore());
        String blackScoreMessage = String.format("BLACK 점수: %.1f", score.getBlackScore());
        return String.join(LINE_SEPARATOR, whiteScoreMessage, blackScoreMessage);
    }

    public String resolveWinnerMessage(Score score) {
        if (score.getWhiteScore() > score.getBlackScore()) {
            return "우승자는 WHITE입니다!";
        }
        return "우승자는 BLACK입니다!";
    }

    public String resolveRoomGuideMessage(List<RoomDto> rooms) {
        String newRoomMessage = String.format("> 새로운 방 생성 : %s", NEW_ROOM.message());
        if (rooms.isEmpty()) {
            return newRoomMessage;
        }
        return String.join(LINE_SEPARATOR, newRoomMessage, resolveRooms(rooms));
    }

    private String resolveRooms(List<RoomDto> rooms) {
        String roomGuideHeaderMessage = String.format("> 입장할 방 선택 : %s 방번호 - 예 %s 1",
                ROOM_SELECTION.message(), ROOM_SELECTION.message());
        String roomListMessage = "> 방 목록 : " + rooms.stream()
                .map(room -> String.valueOf(room.roomId()))
                .collect(Collectors.joining(", "));
        return String.join(LINE_SEPARATOR, roomGuideHeaderMessage, roomListMessage);
    }

    public String resolveEnteringRoomMessage(RoomDto roomDto) {
        return roomDto.roomId() + "번 방에 입장합니다.";
    }

    public String resolveUserNameInputMessage() {
        return String.format("> 사용자명을 입력해 주세요 (4~10자) : %s 사용자명 - 예 %s mangcho",
                FIND_USER.message(), FIND_USER.message());
    }

    public String resolveUserNameMessage(UserDto user) {
        return String.format("%s님 반갑습니다.", user.username());
    }
}
