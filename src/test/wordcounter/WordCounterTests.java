package test.wordcounter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import main.wordcounter.WordCounter;

public class WordCounterTests {
    public boolean executedRule;

    @Before
    public void initialize() {
        executedRule = false;
    }

    @Test
    public void addRuleTest() {
        WordCounter wordCounter = new WordCounter();

        wordCounter.addRule(t -> {
            executedRule = true;
        });

        assertFalse(executedRule);
    }

    @Test
    public void executeNoRulesTest() {
        WordCounter wordCounter = new WordCounter();

        wordCounter.execute();

        assertFalse(executedRule);
    }

    @Test
    public void executeTest() {
        WordCounter wordCounter = new WordCounter();

        wordCounter.addRule(t -> {
            executedRule = true;
        });
        wordCounter.execute();

        assertTrue(executedRule);
    }
}
