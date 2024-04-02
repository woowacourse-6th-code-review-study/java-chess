package chess.dao;

import chess.domain.Position;
import chess.domain.piece.Bishop;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.piece.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PiecesOnChessBoardDAOForMysql implements PiecesOnChessBoardDAO {

    @Override
    public boolean save(Piece piece, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into pieces_on_board (piece_type, team_name, position_name) values ( ?, ? ,? )");
            setPieceToPreparedStatement(List.of(piece), preparedStatement);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveAll(List<Piece> pieces, Connection connection) {
        String sql = pieces.stream().map(piece -> "( ?, ? ,? )")
                .collect(Collectors.joining(","));
        sql = "insert into pieces_on_board (piece_type, team_name, position_name) values " + sql;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setPieceToPreparedStatement(pieces, preparedStatement);
            return preparedStatement.executeUpdate() == pieces.size();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Piece> selectAll(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select piece_type, team_name, position_name from pieces_on_board");
            ResultSet resultSet = preparedStatement.executeQuery();
            return parsingResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isNotEmpty(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select 1 from pieces_on_board where exists(select 1 from pieces_on_board) limit 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Position targetPosition, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from pieces_on_board where position_name = ?");
            preparedStatement.setString(1, targetPosition.name());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from pieces_on_board");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Piece> parsingResultSet(ResultSet resultSet) throws SQLException {
        List<Piece> selected = new ArrayList<>();
        while (resultSet.next()) {
            String piece_type = resultSet.getString(1);
            String team_name = resultSet.getString(2);
            String position_name = resultSet.getString(3);
            PieceType pieceType = PieceType.valueOf(piece_type);
            Team team = Team.valueOf(team_name);
            Position position = Position.valueOf(position_name);
            Piece piece = mapToPiece(pieceType, team, position);
            selected.add(piece);
        }
        return Collections.unmodifiableList(selected);
    }

    private Piece mapToPiece(PieceType pieceType, Team team, Position position) {
        return switch (pieceType) {
            case BISHOP -> new Bishop(position, team);
            case KING -> new King(position, team);
            case KNIGHT -> new Knight(position, team);
            case PAWN -> new Pawn(position, team);
            case QUEEN -> new Queen(position, team);
            case ROOK -> new Rook(position, team);
        };
    }

    private static void setPieceToPreparedStatement(List<Piece> pieces, PreparedStatement preparedStatement)
            throws SQLException {
        for (int index = 0; index < pieces.size(); index++) {
            Piece piece = pieces.get(index);
            PieceType pieceType = piece.getPieceType();
            Team team = piece.getTeam();
            int row = piece.getRow();
            int column = piece.getColumn();
            Position position = Position.getInstance(row, column);
            preparedStatement.setString((index * 3) + 1, pieceType.name());
            preparedStatement.setString((index * 3) + 2, team.name());
            preparedStatement.setString((index * 3) + 3, position.name());
        }
    }
}
