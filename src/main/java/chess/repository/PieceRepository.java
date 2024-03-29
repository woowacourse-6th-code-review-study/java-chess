package chess.repository;

import chess.dto.PiecePlacementDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PieceRepository {
    private static final String TABLE_NAME = "pieces";

    private final ConnectionManager connectionManager;

    public PieceRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void savePiece(PiecePlacementDto piecePlacementDto) throws SQLException {
        String query = String.format("INSERT INTO %s VALUES(?, ?, ?", TABLE_NAME);

        Connection connection = connectionManager.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, piecePlacementDto.getPosition());
        pstmt.setString(2, piecePlacementDto.getTeam());
        pstmt.setString(3, piecePlacementDto.getType());

        pstmt.execute();
    }

    public List<PiecePlacementDto> findPieces() throws SQLException {
        String query = String.format("SELECT * FROM %s", TABLE_NAME);
        Connection connection = connectionManager.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);

        ResultSet resultSet = pstmt.executeQuery();
        List<PiecePlacementDto> result = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String position = resultSet.getString("position");
            String team = resultSet.getString("team");
            String type = resultSet.getString("type");

            result.add(new PiecePlacementDto(position, team, type));
        }

        return result;
    }

    public void deleteAll() throws SQLException {
        String query = String.format("DELETE FROM %s", TABLE_NAME);

        Connection connection = connectionManager.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.executeUpdate();
    }
}
