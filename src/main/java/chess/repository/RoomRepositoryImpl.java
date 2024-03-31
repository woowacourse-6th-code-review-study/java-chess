package chess.repository;

import chess.domain.game.Room;
import chess.domain.game.RoomName;
import chess.domain.piece.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepositoryImpl implements RoomRepository{

    private final DBConnection dbConnection = new DBConnection();

    @Override
    public List<String> findAllRoomNames() {
        List<String> allRoomNames = new ArrayList<>();
        String query = "SELECT name FROM room";
        processQuery(query, preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                allRoomNames.add(rs.getString("name"));
            }
        });
        return allRoomNames;
    }

    @Override
    public boolean existsRoomName(RoomName roomName) {
        List<Boolean> existsRoom = new ArrayList<>();
        final String query = "SELECT EXISTS ("
                + "SELECT 1 FROM room WHERE name = ?) AS exists_room";
        processQuery(query, preparedStatement -> {
            preparedStatement.setString(1, roomName.getValue());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                existsRoom.add(rs.getBoolean("exists_room"));
            }
        });
        return existsRoom.get(0);
    }

    @Override
    public void saveRoom(RoomName roomName) {
        final String query = "INSERT INTO room(name, turn) VALUES(?, ?)";
        processQuery(query, preparedStatement -> {
            preparedStatement.setString(1, roomName.getValue());
            preparedStatement.setString(2, Color.WHITE.name());
            preparedStatement.executeUpdate();
        });
    }

    @Override
    public void updateRoomTurn(Color color, Long roomId) {
        final String query = "UPDATE room SET turn = ? WHERE id = ?";
        processQuery(query, preparedStatement -> {
            preparedStatement.setString(1, color.name());
            preparedStatement.setLong(2, roomId);
            preparedStatement.executeUpdate();
        });
    }

    @Override
    public Room findRoomByName(RoomName roomName) {
        List<Room> roomId = new ArrayList<>();
        final String query = "SELECT * FROM room WHERE name = ?";
        processQuery(query, preparedStatement -> {
            preparedStatement.setString(1, roomName.getValue());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                roomId.add(new Room(rs.getLong("id"), rs.getString("name")));
            }
        });
        return roomId.get(0);
    }

    @Override
    public Color findTurnById(Long roomId) {
        List<Color> color = new ArrayList<>();
        final String query = "SELECT turn FROM room WHERE id = ?";
        processQuery(query, preparedStatement -> {
            preparedStatement.setLong(1, roomId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                color.add(Color.valueOf(rs.getString("turn")));
            }
        });
        return color.get(0);
    }

    private void processQuery(String query, QueryProcessor queryProcessor) {
        try (final Connection connection = dbConnection.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            queryProcessor.process(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
