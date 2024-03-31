package db.dto;

import model.Camp;
import model.Turn;

public record TurnDto(String currentCamp, int count) {

    public static TurnDto from(final Camp camp, final Turn turn) {
        final CampType campType = CampType.findByCamp(camp);
        return new TurnDto(campType.getColorName(), turn.count());
    }
}
