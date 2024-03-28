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

public class PiecesOnChessBoardDAOForMysql implements PiecesOnChessBoardDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:13306/chess?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER_NAME = "user";
    private static final String DB_USER_PASSWORD = "password";
    private final DBConnectionCache dbConnectionCache;

    public PiecesOnChessBoardDAOForMysql() {
        this(DB_URL, DB_USER_NAME, DB_USER_PASSWORD);
    }

    public PiecesOnChessBoardDAOForMysql(String dbUrl, String userName, String password) {
        this.dbConnectionCache = new DBConnectionCache(dbUrl, userName, password);
    }

    @Override
    public List<Piece> selectAll() {
        Connection connection = dbConnectionCache.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select piece_type, team_name, position_name from pieces_on_board");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Piece> selected = parsingResultSet(resultSet);
            return selected;
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

    @Override
    public boolean delete(Position targetPosition) {
        try {
            Connection connection = dbConnectionCache.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from pieces_on_board where position_name = ?");
            preparedStatement.setString(1, targetPosition.name());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Piece piece) {
        try {
            Connection connection = dbConnectionCache.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into pieces_on_board (piece_type, team_name, position_name) values ( ?, ? ,? )");
            PieceType pieceType = piece.getPieceType();
            Team team = piece.getTeam();
            int row = piece.getRow();
            int column = piece.getColumn();
            Position position = Position.getInstance(row, column);
            preparedStatement.setString(1, pieceType.name());
            preparedStatement.setString(2, team.name());
            preparedStatement.setString(3, position.name());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Position targetPosition, PieceType pieceType, Team team) {
        try {
            Connection connection = dbConnectionCache.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update pieces_on_board set piece_type = ?, team_name = ? where position_name = ?");
            preparedStatement.setString(1, pieceType.name());
            preparedStatement.setString(2, team.name());
            preparedStatement.setString(3, targetPosition.name());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
