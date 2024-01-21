package main.wordcounter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WordCounter {
    private final List<Consumer<WordTable>> rules = new ArrayList<>();

    public void execute() {
        WordTable wordTable = new WordTable();

        for (Consumer<WordTable> rule : rules) {
            rule.accept(wordTable);
        }
    }

    public void addRule(Consumer<WordTable> rule) {
        rules.add(rule);
    }
}
