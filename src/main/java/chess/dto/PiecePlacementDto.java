package chess.dto;

import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.repository.mapper.ValueMapper;

public class PiecePlacementDto {
    private final String position;
    private final String team;
    private final String type;

    public PiecePlacementDto(String position, String team, String type) {
        this.position = position;
        this.team = team;
        this.type = type;
    }

    public static PiecePlacementDto of(Piece piece, Position position) {
        return new PiecePlacementDto(
                ValueMapper.mapPositionToString(position),
                ValueMapper.mapTeamToString(piece.getTeam()),
                ValueMapper.mapPieceTypeToString(piece));
    }

    public String getPosition() {
        return position;
    }

    public String getTeam() {
        return team;
    }

    public String getType() {
        return type;
    }
}
