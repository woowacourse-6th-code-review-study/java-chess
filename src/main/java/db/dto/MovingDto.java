package db.dto;

import java.util.List;
import model.Camp;

public record MovingDto(String camp, String current, String next) {

    public static MovingDto from(final List<String> moving, final Camp camp) {
        final String current = moving.get(0);
        final String next = moving.get(1);
        return new MovingDto(camp.toString(), current, next);
    }
}
