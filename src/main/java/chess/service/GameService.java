package chess.service;

import chess.domain.board.BoardFactory;
import chess.domain.board.position.Position;
import chess.domain.game.Room;
import chess.domain.game.RoomName;
import chess.domain.game.Score;
import chess.domain.game.Winner;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.repository.BoardRepository;
import chess.repository.RoomRepository;
import chess.service.dto.ChessGameResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameService {

    private static final int GAME_OVER_KING_COUNT = 1;
    private static final int PWAN_DUPLICATE_THRESHOLD = 2;
    private static final double PWAN_DEDUCTION_SCORE = 0.5;

    private final RoomRepository roomRepository;
    private final BoardRepository boardRepository;

    public GameService(RoomRepository roomRepository, BoardRepository boardRepository) {
        this.roomRepository = roomRepository;
        this.boardRepository = boardRepository;
    }

    public List<String> findAllRoomNames() {
        return roomRepository.findAllRoomNames();
    }

    public Room loadRoom(String input) {
        RoomName roomName = new RoomName(input);
        if (roomRepository.existsRoomName(roomName)) {
            return roomRepository.findRoomByName(roomName);
        }
        return createRoom(roomName);
    }

    private Room createRoom(RoomName roomName) {
        roomRepository.saveRoom(roomName);
        Room room = roomRepository.findRoomByName(roomName);
        initializeBoard(room.getId());
        return room;
    }

    private void initializeBoard(Long roomId) {
        BoardFactory boardFactory = new BoardFactory();
        Map<Position, Piece> board = boardFactory.initialize();
        board.forEach((position, piece) -> boardRepository.savePiece(piece, position, roomId));
    }

    public ChessGameResult generateGameResult(Long roomId) {
        Map<Color, Score> teamScore = getScore(roomId);
        List<Piece> kings = boardRepository.findPieceByPieceType(PieceType.KING, roomId);
        if (kings.size() == GAME_OVER_KING_COUNT) {
            return new ChessGameResult(Winner.selectWinnerByCheckmate(kings.get(0).getColor()), teamScore);
        }
        return new ChessGameResult(Winner.selectWinnerByScore(teamScore), teamScore);
    }

    public Map<Color, Score> getScore(Long roomId) {
        Map<Color, Score> teamScore = new HashMap<>();
        teamScore.put(Color.WHITE, calculateTotalScore(Color.WHITE, PieceType.WHITE_PAWN, roomId));
        teamScore.put(Color.BLACK, calculateTotalScore(Color.BLACK, PieceType.BLACK_PAWN, roomId));
        return teamScore;
    }

    private Score calculateTotalScore(Color color, PieceType pieceType, Long roomId) {
        double sum = sumTotalScore(color, roomId);
        double pawnMinus = calculatePawnScore(pieceType, roomId);
        return new Score(sum - pawnMinus);
    }

    private double sumTotalScore(Color color, Long roomId) {
        List<Piece> pieces = boardRepository.findPieceByColor(color, roomId);
        return pieces.stream()
                .mapToDouble(Piece::getScore)
                .sum();
    }

    private double calculatePawnScore(PieceType pieceType, Long roomId) {
        List<Integer> pieceCount = boardRepository.getPieceCountByPieceType(pieceType, roomId);
        return pieceCount.stream()
                .filter(count -> count >= PWAN_DUPLICATE_THRESHOLD)
                .mapToDouble(count -> count * PWAN_DEDUCTION_SCORE)
                .sum();
    }
}
