package chess.dao;

import chess.domain.Position;
import chess.domain.game.ChessGame;
import chess.domain.game.command.MoveCommand;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import java.util.List;
import java.util.Optional;

public class ChessPersistence {
    private final PiecesOnChessBoardDAO piecesOnChessBoardDAO;
    private final TurnDAO turnDAO;

    public ChessPersistence() {
        this(new PiecesOnChessBoardDAOForMysql(), new TurnDAOForMysql());
    }

    ChessPersistence(PiecesOnChessBoardDAO piecesOnChessBoardDAO, TurnDAO turnDAO) {
        this.piecesOnChessBoardDAO = piecesOnChessBoardDAO;
        this.turnDAO = turnDAO;
    }

    public ChessGame loadChessGame() {
        if (isSaveDataExist()) {
            List<Piece> pieces = piecesOnChessBoardDAO.selectAll();
            Team currentTeam = turnDAO.select().orElseThrow();
            return new ChessGame(pieces, currentTeam);
        }
        piecesOnChessBoardDAO.deleteAll();
        turnDAO.delete();
        return new ChessGame();
    }

    public boolean isSaveDataExist() {
        Optional<Team> selected = turnDAO.select();
        List<Piece> pieces = piecesOnChessBoardDAO.selectAll();
        return selected.isPresent() && !pieces.isEmpty();
    }

    public boolean saveChessGame(ChessGame chessGame) {
        List<Piece> piecesOnBoard = chessGame.getPiecesOnBoard();
        if (!piecesOnChessBoardDAO.selectAll().isEmpty() && turnDAO.select().isPresent()) {
            return false;
        }
        boolean saveAllPiecesSuccess = piecesOnChessBoardDAO.saveAll(piecesOnBoard);
        Team currentTeam = chessGame.currentTeam();
        boolean saveTurnSuccess = turnDAO.save(currentTeam);
        return saveAllPiecesSuccess && saveTurnSuccess;
    }

    public boolean updateChessGame(ChessGame chessGame, MoveCommand moveCommand) {
        List<Position> positions = moveCommand.getOptions();
        Position from = positions.get(0);
        Position to = positions.get(1);
        boolean deleteFromSuccess = piecesOnChessBoardDAO.delete(from);
        boolean deleteToSuccess = piecesOnChessBoardDAO.delete(to);
        Piece movedPiece = chessGame.getPiecesOnBoard().stream()
                .filter(piece -> piece.isOn(to))
                .findFirst().orElseThrow();
        boolean saveMovedPieceSuccess = piecesOnChessBoardDAO.save(movedPiece);
        Team team = turnDAO.select().orElseThrow();
        boolean updateTurnSuccess = turnDAO.update(team, team.otherTeam());
        return !deleteFromSuccess && deleteToSuccess && saveMovedPieceSuccess && updateTurnSuccess;
    }
}
