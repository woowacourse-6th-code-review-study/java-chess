package chess.repository;

import chess.domain.game.Room;
import chess.domain.game.RoomName;
import chess.domain.piece.Color;
import java.util.List;

public interface RoomRepository {

    List<String> findAllRoomNames();

    boolean existsRoomName(RoomName roomName);

    void saveRoom(RoomName roomName);

    void updateRoomTurn(Color color, Long roomId);

    Room findRoomByName(RoomName roomName);

    Color findTurnById(Long roomId);
}
