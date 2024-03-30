package domain.state;

public class EndState implements State {
    @Override
    public State start() {
        throw new UnsupportedOperationException("게임이 종료됐습니다.");
    }

    @Override
    public State move() {
        throw new UnsupportedOperationException("게임이 종료됐습니다.");
    }

    @Override
    public State end() {
        throw new UnsupportedOperationException("게임이 종료됐습니다.");
    }

    @Override
    public boolean isPlaying() {
        return false;
    }
}
