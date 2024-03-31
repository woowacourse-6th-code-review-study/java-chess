package db.dto;

import model.position.File;
import model.position.Position;
import model.position.Rank;

public record PositionDto(String value) {

    public static PositionDto from(final Position position) {
        return new PositionDto(position.toString());
    }

    public Position toPosition() {
        return new Position(File.from(value.charAt(0)), Rank.from(value.charAt(1)));
    }
}
