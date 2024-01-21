package main.wordcounter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;

public class WordTable extends Hashtable<String, Integer> {
    public void addWordsFromReader(Reader reader) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] wordsInLine = line.toUpperCase().split("[^A-Z]+");
                addWords(wordsInLine);
            }
        }
    }

    private void addWords(String[] words) {
        for (String word : words) {
            if (!word.isBlank()) {
                addWord(word);
            }
        }
    }

    private void addWord(String word) {
        put(word, getOrDefault(word, 0) + 1);
    }
}
