package db.dto;

import model.Camp;

public record TurnDto(String value, int count) {

    public Camp convert() {
        if ("WHITE".equals(value)) {
            return Camp.WHITE;
        }
        return Camp.BLACK;
    }
}
