package chess.dao;

import chess.domain.Position;
import chess.domain.game.ChessGame;
import chess.domain.game.command.MoveCommand;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
            Team currentTeam = turnDAO.select(connection).orElseThrow();
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
            throw new RuntimeException(e);
        }
    }

    public boolean isSaveDataExist() {
        Connection connection = dbConnectionCache.getConnection();
        startTransaction(connection);
        Optional<Team> selected = turnDAO.select(connection);
        List<Piece> pieces = piecesOnChessBoardDAO.selectAll(connection);
        commitTransaction(connection);
        return selected.isPresent() && !pieces.isEmpty();
    }

    private void commitTransaction(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveChessGame(ChessGame chessGame) {
        Connection connection = dbConnectionCache.getConnection();
        List<Piece> piecesOnBoard = chessGame.getPiecesOnBoard();
        startTransaction(connection);
        if (!piecesOnChessBoardDAO.selectAll(connection).isEmpty() && turnDAO.select(connection).isPresent()) {
            return false;
        }
        boolean saveAllPiecesSuccess = piecesOnChessBoardDAO.saveAll(piecesOnBoard, connection);
        Team currentTeam = chessGame.currentTeam();
        boolean saveTurnSuccess = turnDAO.save(currentTeam, connection);
        commitTransaction(connection);
        return saveAllPiecesSuccess && saveTurnSuccess;
    }

    public boolean updateChessGame(ChessGame chessGame, MoveCommand moveCommand) {
        Connection connection = dbConnectionCache.getConnection();
        List<Position> positions = moveCommand.getOptions();
        Position from = positions.get(0);
        Position to = positions.get(1);
        startTransaction(connection);
        boolean deleteFromSuccess = piecesOnChessBoardDAO.delete(from, connection);
        boolean deleteToSuccess = piecesOnChessBoardDAO.delete(to, connection);
        Piece movedPiece = findMovedPiece(chessGame, to);
        boolean saveMovedPieceSuccess = piecesOnChessBoardDAO.save(movedPiece, connection);
        Team team = turnDAO.select(connection).orElseThrow();
        boolean updateTurnSuccess = turnDAO.update(team, team.otherTeam(), connection);
        commitTransaction(connection);
        return !deleteFromSuccess && deleteToSuccess && saveMovedPieceSuccess && updateTurnSuccess;
    }

    private Piece findMovedPiece(ChessGame chessGame, Position to) {
        return chessGame.getPiecesOnBoard().stream()
                .filter(piece -> piece.isOn(to))
                .findFirst().orElseThrow();
    }
}
