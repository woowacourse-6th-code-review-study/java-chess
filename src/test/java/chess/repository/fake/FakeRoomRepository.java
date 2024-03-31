package chess.repository.fake;

import chess.domain.game.Room;
import chess.domain.game.RoomName;
import chess.domain.piece.Color;
import chess.repository.RoomRepository;
import java.util.List;

public class FakeRoomRepository implements RoomRepository {
    @Override
    public List<String> findAllRoomNames() {
        return null;
    }

    @Override
    public boolean existsRoomName(RoomName roomName) {
        return false;
    }

    @Override
    public void saveRoom(RoomName roomName) {

    }

    @Override
    public void updateRoomTurn(Color color, Long roomId) {

    }

    @Override
    public Room findRoomByName(RoomName roomName) {
        return null;
    }

    @Override
    public Color findTurnById(Long roomId) {
        return Color.BLACK;
    }
}
