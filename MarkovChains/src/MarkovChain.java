import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class MarkovChain {

    // HashMap to store words and their following words
    private final HashMap<String, ArrayList<String>> hashmap = new HashMap<>();

    // List to store words that start sentences
    private final List<String> startingWords = new ArrayList<>();

    // Random number generator
    private final Random r = new Random();

    // Method to train the Markov chain using a text file
    public void train(String fileName) throws IOException {
        // Read all lines from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Initialize previousWord as null
        String previousWord = null;

        // Iterate through each line in the text
        for (String line : lines) {
            // Split the line into words
            String[] words = line.split("\\s+");
            // Iterate through each word in the line
            for (String word : words) {
                // Skip empty words
                if (word.isEmpty()) {
                    continue;
                }

                // If there was a previous word
                if (previousWord != null) {
                    // If the previous word is not in the hashmap, add it
                    if (!hashmap.containsKey(previousWord)) {
                        hashmap.put(previousWord, new ArrayList<>());
                    }
                    // Add the current word as a following word for the previous word
                    hashmap.get(previousWord).add(word);
                } else {
                    // If there was no previous word, add the current word as a starting word
                    startingWords.add(word);
                }

                // If the word ends a sentence, set previousWord to null
                if (word.endsWith(".") || word.endsWith("!") || word.endsWith("?")) {
                    previousWord = null;
                } else {
                    // Otherwise, set previousWord to the current word
                    previousWord = word;
                }
            }
        }
    }

    // Method to generate text based on the trained Markov chain
    public String generateText(int wordCount) {
        // If there are no starting words, return an empty string
        if (startingWords.isEmpty()) {
            return "";
        }

        // StringBuilder to build the generated text
        StringBuilder sb = new StringBuilder();

        // Select a random starting word
        String currentWord = startingWords.get(r.nextInt(startingWords.size()));
        sb.append(currentWord);

        // Generate text until the specified word count is reached
        for (int i = 0; i < wordCount; i++) {
            // Get the list of following words for the current word
            List<String> nextWords = hashmap.get(currentWord);
            // If there are no following words, break the loop
            if (nextWords == null || nextWords.isEmpty()) {
                break;
            }
            // Select a random following word
            currentWord = nextWords.get(r.nextInt(nextWords.size()));
            // Append the following word to the generated text
            sb.append(" ").append(currentWord);
        }

        // Return the generated text
        return sb.toString();
    }
}