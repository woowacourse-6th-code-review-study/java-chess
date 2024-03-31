package db.dto;

import java.util.Arrays;
import model.Camp;

public enum CampType {

    WHITE(Camp.WHITE, "WHITE"),
    BLACK(Camp.BLACK, "BLACK");

    private final Camp camp;
    private final String colorName;

    CampType(final Camp camp, final String colorName) {
        this.camp = camp;
        this.colorName = colorName;
    }

    public static Camp findByColorName(final String color) {
        return Arrays.stream(values())
                .filter(campType -> campType.colorName.equals(color))
                .findFirst()
                .orElseThrow()
                .camp;
    }
}
