package dao;

import database.JdbcConnectionPool;
import domain.piece.PieceColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductionPieceColorDao implements PieceColorDao {
    private final JdbcConnectionPool connectionPool;

    public ProductionPieceColorDao(final JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public PieceColor find() {
        final String query = "SELECT * FROM current_player_color";
        Connection connection = connectionPool.getConnection();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return convertPieceColor(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void save(final PieceColor playerColor) {
        final String query = "INSERT INTO current_player_color (player_color) VALUES (?)";
        Connection connection = connectionPool.getConnection();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, playerColor.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private PieceColor convertPieceColor(final ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return PieceColor.of(resultSet.getString(2));
        }

        throw new IllegalArgumentException("현재 플레이어의 색상 정보가 존재하지 않습니다.");
    }

    @Override
    public void update(final PieceColor changeColor) {
        final String query = "UPDATE current_player_color SET player_color=?";
        Connection connection = connectionPool.getConnection();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, changeColor.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void delete() {
        final String query = "DELETE FROM current_player_color";
        Connection connection = connectionPool.getConnection();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
