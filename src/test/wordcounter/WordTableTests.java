package test.wordcounter;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import main.wordcounter.WordTable;

public class WordTableTests {
    @Test
    public void addWordsFromReaderTest() throws IOException {
        WordTable wordTable = new WordTable();

        try (StringReader reader = new StringReader("Hello world, hello.")) {
            wordTable.addWordsFromReader(reader);
        }

        assertEquals(2, wordTable.get("HELLO").intValue());
        assertEquals(1, wordTable.get("WORLD").intValue());
    }

    @Test
    public void addWordsFromEmptyReaderTest() throws IOException {
        WordTable wordTable = new WordTable();

        try (StringReader reader = new StringReader("")) {
            wordTable.addWordsFromReader(reader);
        }

        assertEquals(0, wordTable.size());
    }
}
