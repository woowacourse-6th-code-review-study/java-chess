package chess.domain.board;

import static chess.domain.board.InitialPieces.INITIAL_PIECES;

import chess.domain.Position;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceMoveResult;
import chess.domain.piece.PieceType;
import chess.domain.piece.Team;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChessBoard {
    private final List<Piece> piecesOnBoard;
    private Team currentTeam = Team.WHITE;

    public ChessBoard() {
        this(INITIAL_PIECES);
    }

    public ChessBoard(List<Piece> pieces) {
        piecesOnBoard = new ArrayList<>(pieces);
    }

    public ChessBoard(Piece... pieces) {
        this(List.of(pieces));
    }

    PieceMoveResult move(Position from, Position to) {
        if (isEmptyPosition(from) || isOtherTeamTurn(from)) {
            return PieceMoveResult.FAILURE;
        }
        Piece piece = findPiece(from);
        PieceMoveResult moveResult = piece.move(to, this);
        if (moveResult.equals(PieceMoveResult.CATCH)) {
            boolean isGameOver = piecesOnBoard.stream().filter(piece1 -> piece1.getPieceType().equals(PieceType.KING))
                    .filter(piece1 -> piece1.getTeam().equals(currentTeam.otherTeam()))
                    .allMatch(piece1 -> piece1.isOn(to));
            if (isGameOver && currentTeam.equals(Team.WHITE)) {
                return PieceMoveResult.WHITE_WIN;
            }
            if (isGameOver && currentTeam.equals(Team.BLACK)) {
                return PieceMoveResult.BLACK_WIN;
            }
        }
        removePieceIfCaught(to, moveResult);
        changeCurrentTeamIfNotFail(moveResult);
        return moveResult;
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
                .filter(piece -> {
                    Team pieceTeam = piece.getTeam();
                    Team otherTeam = currentTeam.otherTeam();
                    return pieceTeam.equals(otherTeam);
                })
                .findFirst().orElseThrow();
        piecesOnBoard.remove(needToRemovePiece);
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
        double totalPoint = calculateWithOutSameColumnPawn(team);
        Map<Integer, List<Piece>> pawnsAtSameColumn = pawnGroupingByColumn(team);
        double correctionPoint = calculatePawnCorrectionPoint(pawnsAtSameColumn);
        return totalPoint - correctionPoint;
    }

    private double calculateWithOutSameColumnPawn(Team team) {
        return piecesOnBoard.stream()
                .filter(piece -> piece.isTeamWith(team))
                .mapToDouble(Piece::getPoint)
                .reduce(0.0, Double::sum);
    }

    private Map<Integer, List<Piece>> pawnGroupingByColumn(Team team) {
        return piecesOnBoard.stream()
                .filter(piece -> piece.isTeamWith(team))
                .filter(this::isPawn)
                .collect(Collectors.groupingBy(Piece::getColumn, Collectors.toUnmodifiableList()));
    }

    private boolean isPawn(Piece piece) {
        PieceType pieceType = piece.getPieceType();
        return pieceType.equals(PieceType.PAWN);
    }

    private double calculatePawnCorrectionPoint(Map<Integer, List<Piece>> pawnsAtSameColumn) {
        final double correctionPointPerPawn = 0.5;
        return pawnsAtSameColumn.values().stream()
                .filter(pieces -> pieces.size() > 1)
                .mapToDouble(pieces -> pieces.size() * correctionPointPerPawn)
                .reduce(0.0, Double::sum);
    }
}
