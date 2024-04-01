package model.command;

import constant.ErrorCode;
import exception.InvalidCommandException;
import java.util.Arrays;
import java.util.regex.Pattern;

public enum Command {

    START(Pattern.compile("start"), 0),
    MOVE(Pattern.compile("move"), 2),
    POSITION(Pattern.compile("[a-hA-H][1-8]"), 0),
    STATUS(Pattern.compile("status"), 0),
    QUIT(Pattern.compile("quit"), 0),
    END(Pattern.compile("end"), 0);

    private final Pattern pattern;
    private final int bodySize;

    Command(final Pattern pattern, final int bodySize) {
        this.pattern = pattern;
        this.bodySize = bodySize;
    }

    public static Command from(String value) {
        return Arrays.stream(values())
                .filter(command -> command.pattern.matcher(value).matches())
                .findFirst()
                .orElseThrow(() -> new InvalidCommandException(ErrorCode.INVALID_COMMAND));
    }

    public boolean isEqualToBodySize(int targetSize) {
        return bodySize == targetSize;
    }
}
