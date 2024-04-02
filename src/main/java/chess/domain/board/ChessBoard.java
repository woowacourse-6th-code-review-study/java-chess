package chess.domain.board;

import static chess.domain.board.InitialPieces.INITIAL_PIECES;
import static chess.domain.piece.Team.WHITE;

import chess.domain.Position;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceMoveResult;
import chess.domain.piece.PieceType;
import chess.domain.piece.Team;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ChessBoard {
    private final List<Piece> piecesOnBoard;
    private Team currentTeam;

    public ChessBoard() {
        this(INITIAL_PIECES);
    }

    public ChessBoard(List<Piece> pieces) {
        this(pieces, WHITE);
    }

    public ChessBoard(List<Piece> pieces, Team currentTeam) {
        this.piecesOnBoard = new ArrayList<>(pieces);
        this.currentTeam = currentTeam;
    }

    public ChessBoard(Piece... pieces) {
        this(List.of(pieces), WHITE);
    }

    PieceMoveResult move(Position from, Position to) {
        if (isEmptyPosition(from) || isOtherTeamTurn(from)) {
            return PieceMoveResult.FAILURE;
        }
        Piece piece = findPiece(from);
        PieceMoveResult moveResult = piece.move(to, this);
        removePieceIfCaught(to, moveResult);
        moveResult = fixMoveResultWhenGameEnd(to, moveResult);
        changeCurrentTeamIfNotFail(moveResult);
        return moveResult;
    }

    private PieceMoveResult fixMoveResultWhenGameEnd(Position to, PieceMoveResult moveResult) {
        boolean gameEnd = isGameEnd(to);
        if (gameEnd && currentTeam.equals(WHITE)) {
            return PieceMoveResult.WHITE_WIN;
        }
        if (gameEnd && currentTeam.equals(Team.BLACK)) {
            return PieceMoveResult.BLACK_WIN;
        }
        return moveResult;
    }

    private boolean isGameEnd(Position to) {
        return piecesOnBoard.stream()
                .filter(this::isKing)
                .filter(this::isOtherTeam)
                .allMatch(piece -> piece.isOn(to));
    }

    private boolean isKing(Piece piece) {
        return piece.getPieceType().equals(PieceType.KING);
    }

    private boolean isEmptyPosition(Position from) {
        Optional<Piece> optionalPiece = piecesOnBoard.stream()
                .filter(piece -> piece.isOn(from))
                .findFirst();
        return optionalPiece.isEmpty();
    }

    private boolean isOtherTeamTurn(Position from) {
        Piece piece = findPiece(from);
        Team otherTeam = currentTeam.otherTeam();
        Team pieceTeam = piece.getTeam();
        return pieceTeam.equals(otherTeam);
    }

    private Piece findPiece(Position from) {
        return piecesOnBoard.stream()
                .filter(piece -> piece.isOn(from))
                .findFirst().orElseThrow();
    }

    private void removePieceIfCaught(Position to, PieceMoveResult moveResult) {
        if (moveResult.equals(PieceMoveResult.CATCH)) {
            removeDeadPiece(to);
        }
    }

    private void removeDeadPiece(Position to) {
        Piece needToRemovePiece = piecesOnBoard.stream()
                .filter(piece -> piece.isOn(to))
                .filter(this::isOtherTeam)
                .findFirst().orElseThrow();
        piecesOnBoard.remove(needToRemovePiece);
    }

    private boolean isOtherTeam(Piece piece) {
        return piece.isTeamWith(currentTeam.otherTeam());
    }

    private void changeCurrentTeamIfNotFail(PieceMoveResult moveResult) {
        if (!moveResult.equals(PieceMoveResult.FAILURE)) {
            currentTeam = currentTeam.otherTeam();
        }
    }

    public Optional<Team> whichTeam(Position position) {
        Optional<Piece> pieceOnPosition = piecesOnBoard.stream().filter(piece -> piece.isOn(position))
                .findFirst();
        return pieceOnPosition.map(Piece::getTeam);
    }

    List<Piece> getPiecesOnBoard() {
        return Collections.unmodifiableList(piecesOnBoard);
    }

    double calculatePoint(Team team) {
        return PointCalculator.calculatePoint(team, piecesOnBoard);
    }

    Team getCurrentTeam() {
        return currentTeam;
    }
}
