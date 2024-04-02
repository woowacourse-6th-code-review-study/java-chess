package chess.dao;

import chess.dao.exception.DBException;
import chess.domain.Position;
import chess.domain.game.ChessGame;
import chess.domain.game.command.MoveCommand;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ChessPersistence {
    private final PiecesOnChessBoardDAO piecesOnChessBoardDAO;
    private final TurnDAO turnDAO;
    private final DBConnectionCache dbConnectionCache;

    public ChessPersistence() {
        this(new PiecesOnChessBoardDAOForMysql(), new TurnDAOForMysql(), new MysqlDBConnectionCache());
    }

    ChessPersistence(PiecesOnChessBoardDAO piecesOnChessBoardDAO, TurnDAO turnDAO,
                     DBConnectionCache dbConnectionCache) {
        this.piecesOnChessBoardDAO = piecesOnChessBoardDAO;
        this.turnDAO = turnDAO;
        this.dbConnectionCache = dbConnectionCache;
    }

    public ChessGame loadChessGame() {
        Connection connection = dbConnectionCache.getConnection();
        startTransaction(connection);
        if (isSaveDataExist()) {
            List<Piece> pieces = piecesOnChessBoardDAO.selectAll(connection);
            Team currentTeam = turnDAO.select(connection).get();
            commitTransaction(connection);
            return new ChessGame(pieces, currentTeam);
        }
        piecesOnChessBoardDAO.deleteAll(connection);
        turnDAO.delete(connection);
        commitTransaction(connection);
        return new ChessGame();
    }

    private void startTransaction(Connection connection) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DBException("DB 접근에 오류가 발생했습니다.", e);
        }
    }

    public boolean isSaveDataExist() {
        Connection connection = dbConnectionCache.getConnection();
        boolean turnNotEmpty = turnDAO.isNotEmpty(connection);
        boolean piecesNotEmpty = piecesOnChessBoardDAO.isNotEmpty(connection);
        return turnNotEmpty && piecesNotEmpty;
    }

    private void commitTransaction(Connection connection) {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DBException("DB 접근에 오류가 발생했습니다.", e);
        }
    }

    public boolean saveChessGame(ChessGame chessGame) {
        Connection connection = dbConnectionCache.getConnection();
        List<Piece> piecesOnBoard = chessGame.getPiecesOnBoard();
        startTransaction(connection);
        if (canNotSave(connection)) {
            return false;
        }
        boolean saveAllPiecesSuccess = piecesOnChessBoardDAO.saveAll(piecesOnBoard, connection);
        Team currentTeam = chessGame.currentTeam();
        boolean saveTurnSuccess = turnDAO.save(currentTeam, connection);
        commitTransaction(connection);
        return saveAllPiecesSuccess && saveTurnSuccess;
    }

    private boolean canNotSave(Connection connection) {
        boolean piecesNotEmpty = piecesOnChessBoardDAO.isNotEmpty(connection);
        boolean turnNotEmpty = turnDAO.isNotEmpty(connection);
        return piecesNotEmpty || turnNotEmpty;
    }

    public boolean updateChessGame(ChessGame chessGame, MoveCommand moveCommand) {
        Connection connection = dbConnectionCache.getConnection();
        List<Position> positions = moveCommand.getOptions();
        Position from = positions.get(0);
        Position to = positions.get(1);
        startTransaction(connection);
        boolean deleteFromSuccess = piecesOnChessBoardDAO.delete(from, connection);
        piecesOnChessBoardDAO.delete(to, connection);
        Piece movedPiece = findMovedPiece(chessGame, to);
        boolean saveMovedPieceSuccess = piecesOnChessBoardDAO.save(movedPiece, connection);
        Team team = turnDAO.select(connection)
                .orElseThrow(() -> new DBException("데이터가 잘못되었습니다.", null));
        boolean updateTurnSuccess = turnDAO.update(team, team.otherTeam(), connection);
        commitTransaction(connection);
        return deleteFromSuccess && saveMovedPieceSuccess && updateTurnSuccess;
    }

    private Piece findMovedPiece(ChessGame chessGame, Position to) {
        return chessGame.getPiecesOnBoard().stream()
                .filter(piece -> piece.isOn(to))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("움직인 말이 없습니다.", null));
    }

    public void deleteAll() {
        Connection connection = dbConnectionCache.getConnection();
        startTransaction(connection);
        piecesOnChessBoardDAO.deleteAll(connection);
        turnDAO.delete(connection);
        commitTransaction(connection);
    }
}
