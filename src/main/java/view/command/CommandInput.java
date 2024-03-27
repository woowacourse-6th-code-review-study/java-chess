package view.command;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class CommandInput {
    private static final Pattern POSITION_INPUT_PATTERN = Pattern.compile("^[a-h][1-8]$");

    private final List<String> inputs;

    public CommandInput(final String input) {
        this(List.of(input.split(" ")));
    }

    private CommandInput(final List<String> inputs) {
        validatePositions(inputs.subList(1, inputs.size()));
        this.inputs = inputs;
    }

    private void validatePositions(final List<String> inputs) {
        inputs.forEach(this::validatePositionFormat);
    }

    private void validatePositionFormat(final String positionInput) {
        if (!POSITION_INPUT_PATTERN.matcher(positionInput).matches()) {
            throw new IllegalArgumentException();
        }
    }

    public String prefix() {
        if (inputs.isEmpty()) {
            throw new NoSuchElementException();
        }
        return inputs.get(0);
    }

    public List<String> getArguments() {
        return inputs.stream()
                .map(String::toUpperCase)
                .toList()
                .subList(1, inputs.size());
    }
}
