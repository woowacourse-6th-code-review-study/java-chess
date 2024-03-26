package chess.domain.game.command;

import java.util.List;

public class StatusCommand extends Command {
    public static final StatusCommand STATUS_COMMAND = new StatusCommand();

    private StatusCommand() {
        this(null);
    }

    private StatusCommand(String[] options) {
        super(options);
    }

    @Override
    public <T> List<T> getOptions() {
        throw new UnsupportedOperationException();
    }
}
