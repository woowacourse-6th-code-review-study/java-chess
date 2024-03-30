package domain.state;

public class RunningState implements State {
    @Override
    public State start() {
        throw new UnsupportedOperationException("게임이 이미 시작됐습니다.");
    }

    @Override
    public State move() {
        return this;
    }

    @Override
    public State end() {
        return new EndState();
    }

    @Override
    public boolean isPlaying() {
        return true;
    }
}
