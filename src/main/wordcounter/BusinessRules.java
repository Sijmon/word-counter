package main.wordcounter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.function.Consumer;

public class BusinessRules {
    public static Consumer<WordTable> countWordsInFilesRule(File directory) {
        return table -> {
            File[] files = directory.listFiles(file -> file.isFile() && !"exclude.txt".equals(file.getName()));
            for (File file : files) {
                try (FileReader reader = new FileReader(file)) {
                    table.addWordsFromReader(reader);
                } catch (IOException e) {
                    System.out.println("[Error] Could not fully process input file: \"" + file.getName() + "\"");
                    System.out.println("[Error] IOException details: " + e.getMessage());
                }
            }
        };
    }

    public static Consumer<WordTable> excludeWordsRule(WordTable excludeWordTable, File outputDirectory) {
        return table -> {
            int amountExcluded = 0;
            for (String word : excludeWordTable.keySet()) {
                Integer amount = table.get(word);
                if (amount != null) {
                    table.remove(word);
                    amountExcluded += amount;
                }
            }

            writeTextToFile(new File(outputDirectory, "excludeResults.txt"),
                    "Amount of encountered excluded words: " + amountExcluded);
        };
    }

    public static Consumer<WordTable> saveWordOccurrencesToFilesRule(WordTable excludeWordTable, File outputDirectory) {
        return table -> {
            Hashtable<Character, StringBuilder> wordsByLetter = new Hashtable<>();
            for (Character c = 'A'; c <= 'Z'; c++) {
                wordsByLetter.put(c, new StringBuilder());
            }

            table.forEach((key, value) -> {
                String text = key + " " + value + System.lineSeparator();
                wordsByLetter.get(key.charAt(0)).append(text);
            });

            wordsByLetter.forEach((key, value) -> {
                writeTextToFile(new File(outputDirectory, "FILE_" + key + ".txt"), value.toString());
            });
        };
    }

    private static void writeTextToFile(File file, String text) {
        if (!file.isFile()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("[Error] Could not create file: \"" + file.getName() + "\"");
                System.out.println("[Error] IOException details: " + e.getMessage());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException e) {
            System.out.println("[Error] Could not append text to file: \"" + file.getName() + "\"");
            System.out.println("[Error] IOException details: " + e.getMessage());
        }
    }
}
