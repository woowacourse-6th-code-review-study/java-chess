package chess.dao;

import chess.domain.Team;
import chess.domain.piece.Piece;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.dto.PieceType;
import chess.dto.TeamType;

public class PieceEntity {

    private final Position position;
    private final PieceType pieceType;
    private final TeamType teamType;

    public PieceEntity(Position position, Piece piece, Team team) {
        this(position, PieceType.from(piece), TeamType.from(team));
    }

    private PieceEntity(Position position, PieceType pieceType, TeamType teamType) {
        this.position = position;
        this.pieceType = pieceType;
        this.teamType = teamType;
    }

    public static PieceEntity createEmptyPiece(Position position) {
        PieceType pieceType = PieceType.getEmptyType();
        TeamType teamType = TeamType.getEmptyType();

        return new PieceEntity(position, pieceType, teamType);
    }

    public Rank getRank() {
        return position.getRank();
    }

    public File getFile() {
        return position.getFile();
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public TeamType getTeamType() {
        return teamType;
    }
}
