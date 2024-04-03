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

public class ProductionPieceDao implements PieceDao {
    private static final int PIECE_COUNT_INDEX = 1;
    private static final int FILE_INDEX = 1;
    private static final int RANK_INDEX = 2;
    private static final int PIECE_TYPE_INDEX = 3;
    private static final int PIECE_COLOR_INDEX = 4;
    private static final int DESTINATION_FILE_INDEX = 3;
    private static final int DESTINATION_RANK_INDEX = 4;

    private final JdbcConnectionPool connectionPool;

    public ProductionPieceDao(final JdbcConnectionPool connectionPool) {
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
                int pieceCount = resultSet.getInt(PIECE_COUNT_INDEX);
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
    public void save(final PieceEntity piece) {
        final String query = "INSERT INTO piece (file, `rank`, type, color) VALUES (?, ?, ?, ?)";
        Connection connection = connectionPool.getConnection();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(FILE_INDEX, piece.file().name());
            preparedStatement.setInt(RANK_INDEX, piece.rank().value());
            preparedStatement.setString(PIECE_TYPE_INDEX, piece.pieceType().name());
            preparedStatement.setString(PIECE_COLOR_INDEX, piece.pieceColor().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void update(final File sourceFile, final Rank sourceRank, final File destinationFile, final Rank destinationRank) {
        final String query = "UPDATE piece SET file=?, `rank`=? WHERE file=? AND `rank`=?";
        Connection connection = connectionPool.getConnection();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(FILE_INDEX, destinationFile.name());
            preparedStatement.setInt(RANK_INDEX, destinationRank.value());
            preparedStatement.setString(DESTINATION_FILE_INDEX, sourceFile.name());
            preparedStatement.setInt(DESTINATION_RANK_INDEX, sourceRank.value());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void delete(final File file, final Rank rank) {
        final String query = "DELETE FROM piece WHERE file=? AND `rank`=?";
        Connection connection = connectionPool.getConnection();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(FILE_INDEX, file.name());
            preparedStatement.setInt(RANK_INDEX, rank.value());
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
