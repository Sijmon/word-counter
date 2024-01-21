package main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import main.wordcounter.BusinessRules;
import main.wordcounter.WordCounter;
import main.wordcounter.WordTable;

public class Main {
    public static void main(String[] args) {
        String directoryPath = args.length > 0 ? args[0] : promptForDirectoryPath();

        // Validate directory path
        File inputDirectory = new File(directoryPath);
        if (!inputDirectory.isDirectory()) {
            System.out.println("[Error] Invalid directory path");
            return;
        }

        // Validate "exclude.txt" file exists
        File excludeFile = new File(inputDirectory, "exclude.txt");
        if (!excludeFile.isFile()) {
            System.out.println("[Error] Missing \"exclude.txt\" file in directory");
            return;
        }

        // Get words from "exclude.txt" file
        WordTable excludeWordTable = new WordTable();
        try (FileReader reader = new FileReader(excludeFile)) {
            excludeWordTable.addWordsFromReader(reader);
        } catch (IOException e) {
            System.out.println("[Error] Could not read words from \"exclude.txt\" file");
            System.out.println("[Error] IOException details: " + e.getMessage());
            return;
        }

        // Ensure output folder exists
        File outputDirectory = new File("output");
        if (!outputDirectory.isDirectory()) {
            outputDirectory.mkdir();
        }

        WordCounter wordCounter = new WordCounter();
        wordCounter.addRule(BusinessRules.countWordsInFilesRule(inputDirectory));
        wordCounter.addRule(BusinessRules.excludeWordsRule(excludeWordTable, outputDirectory));
        wordCounter.addRule(BusinessRules.saveWordOccurrencesToFilesRule(excludeWordTable, outputDirectory));
        wordCounter.execute();
    }

    private static String promptForDirectoryPath() {
        System.out.print("Enter a directory path: ");
        return System.console().readLine();
    }
}
