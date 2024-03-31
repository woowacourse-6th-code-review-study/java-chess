package chess.repository;

import chess.domain.board.position.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardRepositoryImpl implements BoardRepository {

    private final DBConnection dbConnection = new DBConnection();

    @Override
    public void savePiece(Piece piece, Position position) {
        final String query = "INSERT INTO board(`row`, `column`, piece_type, piece_color) VALUES(?, ?, ?, ?)";
        processQuery(query, preparedStatement -> {
            preparedStatement.setInt(1, position.getRowIndex());
            preparedStatement.setString(2, position.getColumn().name());
            preparedStatement.setString(3, piece.getPieceType().name());
            preparedStatement.setString(4, piece.getColor().name());
            preparedStatement.executeUpdate();
        });
    }

    @Override
    public boolean existsPieceByPosition(Position position) {
        List<Boolean> existsPiece = new ArrayList<>();
        final String query = "SELECT EXISTS ("
                + "SELECT 1 FROM board WHERE `row` = ? AND `column` = ?) AS exists_piece";
        processQuery(query, preparedStatement -> {
            preparedStatement.setInt(1, position.getRowIndex());
            preparedStatement.setString(2, position.getColumn().name());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                existsPiece.add(rs.getBoolean("exists_piece"));
            }
        });
        return existsPiece.get(0);
    }

    @Override
    public void deletePieceByPosition(Position position) {
        final String query = "DELETE FROM board WHERE `row` = ? AND `column` = ?";
        processQuery(query, preparedStatement -> {
            preparedStatement.setInt(1, position.getRowIndex());
            preparedStatement.setString(2, position.getColumn().name());
            preparedStatement.executeUpdate();
        });
    }

    @Override
    public Piece findPieceByPosition(Position position) {
        List<Piece> pieces = new ArrayList<>();
        final String query = "SELECT piece_type, piece_color FROM board WHERE `row` = ? AND `column` = ?";
        processQuery(query, preparedStatement -> {
            preparedStatement.setInt(1, position.getRowIndex());
            preparedStatement.setString(2, position.getColumn().name());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                pieces.add(new Piece(rs.getString("piece_type"), rs.getString("piece_color")));
            }
        });
        return pieces.get(0);
    }

    @Override
    public List<Piece> findPieceByColor(Color piece_color) {
        List<Piece> pieces = new ArrayList<>();
        String query = "SELECT piece_type, piece_color FROM board WHERE piece_color = ?";
        processQuery(query, preparedStatement -> {
            preparedStatement.setString(1, piece_color.name());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Piece piece = new Piece(rs.getString("piece_type"), rs.getString("piece_color"));
                pieces.add(piece);
            }
        });
        return pieces;
    }

    @Override
    public List<Integer> getPieceCountByPieceType(PieceType pieceType) {
        List<Integer> pieceCount = new ArrayList<>();
        String query = "SELECT COUNT(*) AS piece_count FROM board WHERE piece_type = ? GROUP BY `column`";
        processQuery(query, preparedStatement -> {
            preparedStatement.setString(1, pieceType.name());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                pieceCount.add(rs.getInt("piece_count"));
            }
        });
        return pieceCount;
    }

    @Override
    public Map<Position, Piece> findAllPiece() {
        Map<Position, Piece> allPieces = new HashMap<>();
        String query = "SELECT * FROM board";
        processQuery(query, preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Position position = new Position(rs.getInt("row"), rs.getString("column"));
                Piece piece = new Piece(rs.getString("piece_type"), rs.getString("piece_color"));
                allPieces.put(position, piece);
            }
        });
        return allPieces;
    }

    @Override
    public List<Piece> findPieceByPieceType(PieceType pieceType) {
        List<Piece> pieces = new ArrayList<>();
        final String query = "SELECT piece_type, piece_color FROM board WHERE piece_type = ?";
        processQuery(query, preparedStatement -> {
            preparedStatement.setString(1, pieceType.name());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                pieces.add(new Piece(rs.getString("piece_type"), rs.getString("piece_color")));
            }
        });
        return pieces;
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
