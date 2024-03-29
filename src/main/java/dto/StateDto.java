package dto;

import domain.board.State;

public record StateDto(String state) {
    public static StateDto of(State state) {
        return new StateDto(
                state.name()
        );
    }

    public State getState() {
        return State.valueOf(state);
    }
}
