package view.command;

import java.util.List;
import java.util.NoSuchElementException;

public class CommandInput {
    private final List<String> inputs;

    public CommandInput(final String input) {
        this(List.of(input.split(" ")));
    }

    private CommandInput(final List<String> inputs) {
        this.inputs = inputs;
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
