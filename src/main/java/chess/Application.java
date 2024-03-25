package chess;

import static chess.domain.game.EndCommand.END_COMMAND;

import chess.domain.Position;
import chess.domain.game.ChessGame;
import chess.domain.game.Command;
import chess.domain.game.StatusCommand;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Team;
import chess.dto.PieceDTO;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        OutputView.printGuide();
        Command startOrEnd = InputView.readStartOrEnd();
        if (isEndCommand(startOrEnd)) {
            return;
        }
        ChessGame chessGame = new ChessGame();
        List<Piece> piecesOnBoard = chessGame.getPiecesOnBoard();
        List<PieceDTO> pieceDTOS = piecesToDTO(piecesOnBoard);
        OutputView.printChessBoard(pieceDTOS);

        Command endOrMoveOrStatus = InputView.readEndOrMoveOrStatus();
        while (!isEndCommand(endOrMoveOrStatus)) {
            playGameOrPrintStatus(endOrMoveOrStatus, chessGame);
            endOrMoveOrStatus = InputView.readEndOrMoveOrStatus();
        }
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

    private static boolean isEndCommand(Command command) {
        return command.equals(END_COMMAND);
    }

    private static void playGameOrPrintStatus(Command moveOrStatus, ChessGame chessGame) {
        if (moveOrStatus.equals(StatusCommand.STATUS_COMMAND)) {
            printStatus(chessGame);
            return;
        }
        playGame(moveOrStatus, chessGame);
    }

    private static void printStatus(ChessGame chessGame) {
        double whiteTeamPoint = chessGame.calculatePoint(Team.WHITE);
        OutputView.printStatus(Team.WHITE, whiteTeamPoint);
        double blackTeamPoint = chessGame.calculatePoint(Team.BLACK);
        OutputView.printStatus(Team.BLACK, blackTeamPoint);
    }

    private static void playGame(Command moveCommand, ChessGame chessGame) {
        List<Position> options = moveCommand.getOptions();
        Position from = options.get(0);
        Position to = options.get(1);
        boolean moveSuccess = chessGame.move(from, to);
        List<Piece> piecesOnBoard = chessGame.getPiecesOnBoard();
        List<PieceDTO> pieceDTOS = piecesToDTO(piecesOnBoard);
        OutputView.printChessBoard(pieceDTOS);
        if (!moveSuccess) {
            OutputView.printReInputGuide();
        }
    }
}
