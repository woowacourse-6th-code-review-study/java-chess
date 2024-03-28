package chess;

import static chess.domain.game.command.EndCommand.END_COMMAND;
import static chess.domain.piece.PieceMoveResult.FAILURE;
import static chess.domain.piece.Team.BLACK;
import static chess.domain.piece.Team.WHITE;

import chess.dao.PiecesOnChessBoardDAO;
import chess.dao.PiecesOnChessBoardDAOForMysql;
import chess.dao.TurnDAO;
import chess.dao.TurnDAOForMysql;
import chess.domain.Position;
import chess.domain.game.ChessGame;
import chess.domain.game.command.Command;
import chess.domain.game.command.MoveCommand;
import chess.domain.game.command.StatusCommand;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceMoveResult;
import chess.domain.piece.PieceType;
import chess.domain.piece.Team;
import chess.dto.PieceDTO;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Application {
    public static final TurnDAO turnDAO = new TurnDAOForMysql();
    private static final PiecesOnChessBoardDAO piecesOnChessBoardDAO = new PiecesOnChessBoardDAOForMysql();

    public static void main(String[] args) {
        OutputView.printGuide();
        Command startOrEnd = InputView.readStartOrEnd();
        if (isEndCommand(startOrEnd)) {
            return;
        }
        ChessGame chessGame = new ChessGame();
        Optional<Team> selected = turnDAO.select();
        if (selected.isPresent()) {
            List<Piece> pieces = piecesOnChessBoardDAO.selectAll();
            Team team = turnDAO.select().orElse(WHITE);
            chessGame = new ChessGame(pieces, team);
        }
        if (selected.isEmpty()) {
            for (Piece piece : chessGame.getPiecesOnBoard()) {
                piecesOnChessBoardDAO.save(piece);
            }
            turnDAO.save(WHITE);
        }
        printPiecesOnChessBoard(chessGame);

        playChess(chessGame);
    }

    private static boolean isEndCommand(Command command) {
        return command.equals(END_COMMAND);
    }

    private static void printPiecesOnChessBoard(ChessGame chessGame) {
        List<Piece> piecesOnBoard = chessGame.getPiecesOnBoard();
        List<PieceDTO> pieceDTOS = piecesToDTO(piecesOnBoard);
        OutputView.printChessBoard(pieceDTOS);
    }

    private static List<PieceDTO> piecesToDTO(List<Piece> piecesOnBoard) {
        return piecesOnBoard.stream().map(piece -> {
            PieceType pieceType = piece.getPieceType();
            int row = piece.getRow();
            int column = piece.getColumn();
            Team team = piece.getTeam();
            return new PieceDTO(team, pieceType, row, column);
        }).toList();
    }

    private static void playChess(ChessGame chessGame) {
        Command endOrMoveOrStatus;
        PieceMoveResult pieceMoveResult;
        do {
            endOrMoveOrStatus = InputView.readEndOrMoveOrStatus();
            pieceMoveResult = playGameOrPrintStatus(endOrMoveOrStatus, chessGame);
        } while (!isEndCommand(endOrMoveOrStatus) && !pieceMoveResult.isEnd());
    }

    private static PieceMoveResult playGameOrPrintStatus(Command moveOrStatus, ChessGame chessGame) {
        if (moveOrStatus.equals(StatusCommand.STATUS_COMMAND)) {
            printStatus(chessGame);
            return FAILURE;
        }
        return playGame((MoveCommand) moveOrStatus, chessGame);
    }

    private static void printStatus(ChessGame chessGame) {
        Map<Team, Double> scoresGroupingByTeam = chessGame.calculateScores();
        scoresGroupingByTeam.forEach(OutputView::printStatus);
        double whiteTeamPoint = scoresGroupingByTeam.get(WHITE);
        double blackTeamPoint = scoresGroupingByTeam.get(BLACK);
        OutputView.currentWinner(whiteTeamPoint, blackTeamPoint);
    }

    private static PieceMoveResult playGame(MoveCommand moveCommand, ChessGame chessGame) {
        PieceMoveResult moveResult = chessGame.move(moveCommand);
        if (!moveResult.equals(FAILURE)) {
            List<Position> positions = moveCommand.getOptions();
            Position from = positions.get(0);
            Position to = positions.get(1);
            piecesOnChessBoardDAO.delete(from);
            piecesOnChessBoardDAO.delete(to);
            Piece movedPiece = chessGame.getPiecesOnBoard().stream()
                    .filter(piece -> piece.isOn(to))
                    .findFirst().orElseThrow();
            piecesOnChessBoardDAO.save(movedPiece);
            Team team = turnDAO.select().orElseThrow();
            turnDAO.update(team, team.otherTeam());
        }
        printPiecesOnChessBoard(chessGame);
        printReInputGuideIfNeed(moveResult);
        printWinnerIfNeed(moveResult);
        return moveResult;
    }

    private static void printReInputGuideIfNeed(PieceMoveResult moveResult) {
        if (moveResult.equals(FAILURE)) {
            OutputView.printReInputGuide();
        }
    }

    private static void printWinnerIfNeed(PieceMoveResult moveResult) {
        if (moveResult.isEnd()) {
            OutputView.printWinner(moveResult);
        }
    }
}
