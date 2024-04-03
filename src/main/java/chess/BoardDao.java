package chess;

import chess.domain.Board;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.dto.BoardDto;
import chess.dto.PieceDto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BoardDao {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new RuntimeException("DB 연결 실패", e);
        }
    }

    public void add(final int gameId, final BoardDto boardDto) {
        addGame(gameId, boardDto);
        addBoard(gameId, boardDto);
    }

    private void addGame(final int gameId, final BoardDto boardDto) {
        final var query = "INSERT INTO game VALUES(?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, boardDto.getTurn());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("게임 정보 저장 실패", e);
        }
    }

    private void addBoard(final int gameId, final BoardDto boardDto) {
        Map<Position, PieceDto> board = boardDto.getBoardDto();

        final var query = "INSERT INTO board VALUES(?, ?, ?, ?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            addPiece(gameId, board, preparedStatement);
        } catch (final SQLException e) {
            throw new RuntimeException("보드 정보 저장 실패", e);
        }
    }

    private void addPiece(int gameId, Map<Position, PieceDto> board, PreparedStatement preparedStatement)
            throws SQLException {
        for (Entry<Position, PieceDto> pieceEntry : board.entrySet()) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, pieceEntry.getKey().getFile());
            preparedStatement.setString(3, pieceEntry.getKey().getRank());
            preparedStatement.setString(4, pieceEntry.getValue().getType());
            preparedStatement.setString(5, pieceEntry.getValue().getTeam());
            preparedStatement.executeUpdate();
        }
    }

    public void delete(int gameId) {
        deleteGame(gameId);
        deleteBoard(gameId);
    }

    private void deleteGame(int gameId) {
        final var query = "DELETE FROM game WHERE gameId = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 정보 삭제 실패", e);
        }
    }

    private void deleteBoard(int gameId) {
        final var query = "DELETE FROM board WHERE gameId = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("보드 정보 삭제 실패", e);
        }
    }

    public Board loadBoard(int gameId) {
        Team turn = getGameTurn(gameId);
        Map<Position, Piece> pieces = getBoardPieces(gameId);
        return new Board(pieces, turn);
    }

    private Team getGameTurn(int gameId) {
        final String query = "SELECT turn FROM game WHERE gameId = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            Team turn = getTeam(preparedStatement);
            validateExistTeam(turn);
            return turn;
        } catch (SQLException e) {
            throw new RuntimeException("게임 턴 정보 조회 실패", e);
        }
    }

    private Team getTeam(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            return getTeam(resultSet);
        }
    }

    private Team getTeam(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            String turn = resultSet.getString("turn");
            return Team.valueOf(turn.toUpperCase());
        }
        return null;
    }

    private void validateExistTeam(Team turn) {
        if (turn == null) {
            throw new IllegalArgumentException("해당 게임 ID를 찾을 수 없습니다.");
        }
    }

    private Map<Position, Piece> getBoardPieces(int gameId) {
        Map<Position, Piece> pieces = new HashMap<>();
        final String query = "SELECT file, `rank`, type, team FROM board WHERE gameId = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            getOnePiece(preparedStatement, pieces);
        } catch (SQLException e) {
            throw new RuntimeException("보드 정보 조회 실패", e);
        }
        return pieces;
    }

    private void getOnePiece(PreparedStatement preparedStatement, Map<Position, Piece> pieces) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            getOnePiece(pieces, resultSet);
        }
    }

    private void getOnePiece(Map<Position, Piece> pieces, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            File file = File.valueOf(resultSet.getString("file").toUpperCase());
            Rank rank = Rank.valueOf(resultSet.getString("rank").toUpperCase());
            PieceType type = PieceType.valueOf(resultSet.getString("type").toUpperCase());
            Team team = Team.valueOf(resultSet.getString("team").toUpperCase());
            Position position = new Position(file, rank);
            Piece piece = type.createPiece(team);
            pieces.put(position, piece);
        }
    }

    public boolean isExistBoard(int gameId) {
        final String query = "SELECT EXISTS(SELECT 1 FROM game WHERE gameId = ?) AS Exist";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            return getExist(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException("보드 존재 여부 확인 실패", e);
        }
    }

    private boolean getExist(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet rs = preparedStatement.executeQuery()) {
            return getExist(rs);
        }
    }

    private boolean getExist(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getBoolean("Exist");
        }
        return false;
    }
}
