package db.dto;

import model.Camp;
import model.position.Moving;
import model.position.Position;

public record MovingDto(String camp, String currentFile, String currentRank, String nextFile, String nextRank) {

    public static MovingDto from(final Moving moving, final Camp camp) {
        final Position currentPosition = moving.getCurrentPosition();
        final Position nextPosition = moving.getNextPosition();

        final String current = currentPosition.toString();
        final String next = nextPosition.toString();

        return new MovingDto(camp.toString(), String.valueOf(current.charAt(0)), String.valueOf(current.charAt(1)),
                String.valueOf(next.charAt(0)), String.valueOf(next.charAt(1)));
    }
}
