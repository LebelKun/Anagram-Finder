import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AnagramFinder {

    public static void main(String[] args) {
        // Here we check if filename is provided as an argument
        if (args.length < 1) {
            System.out.println("Please provide the filename as an argument.");
            return;
        }

        String fileName = args[0];

        try {
            // Map to store lists of unique words to see if they are anagrams
            Map<String, List<String>> anagramGroups = new HashMap<>();

            // This reads all of the lines from the file and puts it into a Stream
            Stream<String> lines = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8);

            // Process each line
            int count = 0;
            for (String line : Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8)) {
                // Trim and filter
                line = line.trim();
                System.out.print(count + ": " + line + "\n");
                if (line.isEmpty()) {
                    continue;
                }

                // Lowercases the words
                String lowercaseWord = line.toLowerCase();

                // Sorts characters of the word with case sensitivity
                String sortedWord = sortCharsCaseSensitive(lowercaseWord);

                // This checks if the lowercase words are already in the list and associated with the key 
                if (!anagramGroups.containsKey(sortedWord)) {
                    anagramGroups.put(sortedWord, new ArrayList<>());
                }

                List<String> group = anagramGroups.get(sortedWord);
                if (!group.contains(lowercaseWord)) {
                    // If that is not the case, add the lowercase word to the list
                    group.add(lowercaseWord);
                }

            }

            // Close the stream to release resources
            lines.close();
            
            // Print groups of words that are anagrams of each other
            for (List<String> group : anagramGroups.values()) {
                if (group.size() > 1) {
                    System.out.println("Anagrams: " + group);
                }
            }
            System.out.println(anagramGroups.size());

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();  // Print the stack trace for debugging
        }
    }

    // This is a helper method to sort characters in a string with case sensitivity
    private static String sortCharsCaseSensitive(String input) {
        char[] chars = input.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
    
}
