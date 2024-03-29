package db.dto;

import model.position.Position;

public record PositionDto(String value) {

    public static PositionDto from(final Position position) {
        return new PositionDto(position.toString());
    }
}
