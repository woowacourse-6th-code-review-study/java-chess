package chess.repository.mapper;

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

public class ValueMapper {
    private ValueMapper() {
    }

    public static String mapPositionToString(Position position) {
        File file = position.getFile();
        Rank rank = position.getRank();
        return file.name() + rank.name();
    }

    public static String mapTeamToString(Team team) {
        return team.name();
    }

    public static String mapPieceTypeToString(Piece piece) {
        if (piece instanceof Pawn) {
            return "pawn";
        }
        if (piece instanceof Rook) {
            return "rook";
        }
        if (piece instanceof Knight) {
            return "knight";
        }
        if (piece instanceof Bishop) {
            return "bishop";
        }
        if (piece instanceof Queen) {
            return "queen";
        }
        if (piece instanceof King) {
            return "king";
        }
        throw new IllegalArgumentException("알 수 없는 피스 타입입니다");
    }

}
