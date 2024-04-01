package chess.dto;

import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.repository.mapper.ValueMapper;

public class PieceDto {
    private final String position;
    private final String team;
    private final String type;

    public PieceDto(String position, String team, String type) {
        this.position = position;
        this.team = team;
        this.type = type;
    }

    public static PieceDto of(Piece piece, Position position) {
        return new PieceDto(
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
