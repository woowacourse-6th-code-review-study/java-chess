package model;

public class GameTurn {

    private static final Camp STARTING_CAMP = Camp.WHITE;

    private Camp camp;
    private Turn turn;

    private GameTurn(final Camp camp) {
        this.camp = camp;
        this.turn = new Turn(0);
    }

    public GameTurn(final Camp camp, final Turn turn) {
        this.camp = camp;
        this.turn = turn;
    }

    public static GameTurn create() {
        return new GameTurn(STARTING_CAMP);
    }

    public void progress() {
        camp = camp.toggle();
        turn = turn.take();
    }

    public Camp getCamp() {
        return camp;
    }

    public Turn getTurn() {
        return turn;
    }
}
