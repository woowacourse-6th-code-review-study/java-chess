package domain.state;

public class ReadyState implements State {
    @Override
    public State start() {
        return new RunningState();
    }

    @Override
    public State move() {
        throw new UnsupportedOperationException("게임을 시작해 주세요.");
    }

    @Override
    public State end() {
        throw new UnsupportedOperationException("게임을 시작해 주세요.");
    }

    @Override
    public boolean isPlaying() {
        return true;
    }
}
