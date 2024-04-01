package chess.repository.mapper;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Bishop;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.piece.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.dto.PieceDto;
import chess.dto.PiecesDto;
import java.util.HashMap;
import java.util.Map;

public class DomainMapper {
    private static final int FILE_INDEX = 0;
    private static final int RANK_INDEX = 1;

    public static Team mapToTurn(String value) {
        if (value.equals("WHITE")) {
            return Team.WHITE;
        }
        return Team.BLACK;
    }

    public static ChessBoard mapToBoard(PiecesDto piecePlacements) {
        Map<Position, Piece> positionPiece = new HashMap<>();

        for (PieceDto pieceDto : piecePlacements.getPieces()) {
            Position position = mapToPosition(pieceDto.getPosition());
            Team team = mapToTeam(pieceDto.getTeam());
            Piece piece = mapToPiece(pieceDto.getType(), team);

            positionPiece.put(position, piece);
        }

        return new ChessBoard(positionPiece);
    }

    private static Piece mapToPiece(String pieceTypeValue, Team team) {
        if ("pawn".equals(pieceTypeValue)) {
            return new Pawn(team);
        }
        if ("knight".equals(pieceTypeValue)) {
            return new Knight(team);
        }
        if ("bishop".equals(pieceTypeValue)) {
            return new Bishop(team);
        }
        if ("rook".equals(pieceTypeValue)) {
            return new Rook(team);
        }
        if ("queen".equals(pieceTypeValue)) {
            return new Queen(team);
        }
        if ("king".equals(pieceTypeValue)) {
            return new King(team);
        }
        throw new IllegalArgumentException("Invalid piece type: " + pieceTypeValue);
    }

    private static Position mapToPosition(String value) {
        File file = File.from(value.charAt(FILE_INDEX) - 'a');
        Rank rank = Rank.from(8 - (value.charAt(RANK_INDEX) - '0'));
        return new Position(file, rank);
    }

    private static Team mapToTeam(String value) {
        if (value.equals("WHITE")) {
            return Team.WHITE;
        }
        return Team.BLACK;
    }
}
