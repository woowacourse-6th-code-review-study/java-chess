package repository;

import database.dao.PieceDao;
import dto.PieceDto;
import dto.RoomDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PieceMockDao implements PieceDao {
    private Map<String, PieceDto> repository = new HashMap<>();

    public void add(final RoomDto room, final PieceDto piece) {
        String key = generateKey(room, piece);
        repository.put(key, piece);
    }

    public List<PieceDto> findPieceByGameId(final int gameId) {
        return repository.entrySet().stream()
                .filter(entry -> extractGameIdFromKey(entry.getKey()) == gameId)
                .map(Map.Entry::getValue)
                .toList();
    }

    public void deleteAllByGameId(final int gameId) {
        repository = repository.entrySet().stream()
                .filter(entry -> extractGameIdFromKey(entry.getKey()) != gameId)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private String generateKey(RoomDto room, PieceDto piece) {
        return room.roomId() + "-" + piece.boardFile() + "-" + piece.boardRank();
    }

    private int extractGameIdFromKey(String key) {
        return Integer.parseInt(key.split("-")[0]);
    }
}
