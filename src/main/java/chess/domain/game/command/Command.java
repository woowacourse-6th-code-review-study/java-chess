package chess.domain.game.command;

import java.util.List;

public abstract class Command {
    protected final String[] options;

    public Command(String[] options) {
        this.options = options;
    }

    public abstract <T> List<T> getOptions();
}
