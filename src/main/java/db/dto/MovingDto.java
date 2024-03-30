package db.dto;

import java.util.List;
import model.Camp;

public record MovingDto(String camp, String currentFile, String currentRank, String nextFile, String nextRank) {

    public static MovingDto from(final List<String> moving, final Camp camp) {
        final String current = moving.get(0);
        final String next = moving.get(1);

        return new MovingDto(camp.toString(), String.valueOf(current.charAt(0)), String.valueOf(current.charAt(1)),
                String.valueOf(next.charAt(0)), String.valueOf(next.charAt(1)));
    }
}
