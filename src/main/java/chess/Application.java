package chess;

import static chess.domain.game.EndCommand.END_COMMAND;

import chess.domain.Position;
import chess.domain.game.ChessGame;
import chess.domain.game.Command;
import chess.domain.game.StatusCommand;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceMoveResult;
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

        Command endOrMoveOrStatus;
        PieceMoveResult pieceMoveResult;
        do {
            endOrMoveOrStatus = InputView.readEndOrMoveOrStatus();
            pieceMoveResult = playGameOrPrintStatus(endOrMoveOrStatus, chessGame);
        } while (!isEndCommand(endOrMoveOrStatus) && !pieceMoveResult.isEnd());
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

    private static PieceMoveResult playGameOrPrintStatus(Command moveOrStatus, ChessGame chessGame) {
        if (moveOrStatus.equals(StatusCommand.STATUS_COMMAND)) {
            printStatus(chessGame);
            return PieceMoveResult.FAILURE;
        }
        return playGame(moveOrStatus, chessGame);
    }

    private static PieceMoveResult playGame(Command moveCommand, ChessGame chessGame) {
        List<Position> options = moveCommand.getOptions();
        Position from = options.get(0);
        Position to = options.get(1);
        PieceMoveResult moveResult = chessGame.move(from, to);
        List<Piece> piecesOnBoard = chessGame.getPiecesOnBoard();
        List<PieceDTO> pieceDTOS = piecesToDTO(piecesOnBoard);
        OutputView.printChessBoard(pieceDTOS);
        printReInputGuideIfNeed(moveResult);
        OutputView.printWinner(moveResult);
        return moveResult;
    }

    private static void printReInputGuideIfNeed(PieceMoveResult moveResult) {
        if (moveResult.equals(PieceMoveResult.FAILURE)) {
            OutputView.printReInputGuide();
        }
    }

    private static void printStatus(ChessGame chessGame) {
        double whiteTeamPoint = chessGame.calculatePoint(Team.WHITE);
        OutputView.printStatus(Team.WHITE, whiteTeamPoint);
        double blackTeamPoint = chessGame.calculatePoint(Team.BLACK);
        OutputView.printStatus(Team.BLACK, blackTeamPoint);
    }
}
