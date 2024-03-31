package dao;

import database.JdbcConnectionPool;
import domain.board.File;
import domain.board.Rank;
import domain.piece.PieceColor;
import domain.piece.PieceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MysqlPieceDao implements PieceDao {
    private final JdbcConnectionPool connectionPool;

    public MysqlPieceDao(final JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<PieceEntity> findAll() {
        final String query = "SELECT * FROM piece";
        Connection connection = connectionPool.getConnection();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return createPieceEntities(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private List<PieceEntity> createPieceEntities(final ResultSet resultSet) throws SQLException {
        List<PieceEntity> pieceEntities = new ArrayList<>();
        while (resultSet.next()) {
            File file = File.of(resultSet.getString("file"));
            Rank rank = Rank.of(resultSet.getString("rank"));
            PieceType type = PieceType.of(resultSet.getString("type"));
            PieceColor color = PieceColor.of(resultSet.getString("color"));

            pieceEntities.add(new PieceEntity(type, color, file, rank));
        }

        return pieceEntities;
    }

    @Override
    public boolean existPiecePositions() {
        final String query = "SELECT COUNT(*) FROM piece";
        Connection connection = connectionPool.getConnection();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int pieceCount = resultSet.getInt(1);
                return pieceCount > 0;
            }

            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void savePiece(final PieceEntity piece) {
        final String query = "INSERT INTO piece (file, `rank`, type, color) VALUES (?, ?, ?, ?)";
        Connection connection = connectionPool.getConnection();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, piece.file().name());
            preparedStatement.setInt(2, piece.rank().value());
            preparedStatement.setString(3, piece.pieceType().name());
            preparedStatement.setString(4, piece.pieceColor().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void updatePiecePosition(final File sourceFile, final Rank sourceRank, final File destinationFile, final Rank destinationRank) {
        final String query = "UPDATE piece SET file=?, `rank`=? WHERE file=? AND `rank`=?";
        Connection connection = connectionPool.getConnection();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, destinationFile.name());
            preparedStatement.setInt(2, destinationRank.value());
            preparedStatement.setString(3, sourceFile.name());
            preparedStatement.setInt(4, sourceRank.value());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteByFileAndRank(final File file, final Rank rank) {
        final String query = "DELETE FROM piece WHERE file=? AND `rank`=?";
        Connection connection = connectionPool.getConnection();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, file.name());
            preparedStatement.setInt(2, rank.value());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteAll() {
        final String query = "DELETE FROM piece";
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
