package repository;

import dto.PieceDto;
import dto.RoomDto;

import java.util.List;

public interface PieceDao {
    void add(RoomDto room, PieceDto piece);

    List<PieceDto> findPieceByGameId(int gameId);

    void deleteAllByGameId(int gameId);
}
