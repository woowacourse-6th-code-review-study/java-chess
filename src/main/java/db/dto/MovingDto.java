package db.dto;

import model.Camp;
import model.position.Moving;
import model.position.Position;

public record MovingDto(String camp, String current, String next) {

    public static MovingDto from(final Moving moving, final Camp camp) {
        final Position currentPosition = moving.getCurrentPosition();
        final Position nextPosition = moving.getNextPosition();

        return new MovingDto(camp.toString(), currentPosition.toString(), nextPosition.toString());
    }
}
