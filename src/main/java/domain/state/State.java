package domain.state;

public interface State {
    State start();
    State move();
    State end();
    boolean isPlaying();
}
