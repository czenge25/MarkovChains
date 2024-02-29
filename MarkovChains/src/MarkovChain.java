/*
CZ
2/29/24
HK
 */

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class MarkovChain {

    private final HashMap<String, ArrayList<String>> hashmap = new HashMap<>();
    private final List<String> startingWords = new ArrayList<>();
    private final Random r = new Random();

    // Method to train the Markov chain based on input text file
    public void train(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName)); // Read lines from the input file

        String previousWord = null; // Initialize variable to store the previous word

        for (String line : lines) { // Iterate through each line in the input file
            String[] words = line.split("\\s+"); // Split the line into words based on whitespace
            for (String word : words) { // Iterate through each word in the line
                if (word.isEmpty()) { // Skip empty words
                    continue;
                }

                if (previousWord != null) { // If there is a previous word
                    if (!hashmap.containsKey(previousWord)) { // If the hashmap does not contain the previous word
                        hashmap.put(previousWord, new ArrayList<>()); // Add the previous word to the hashmap
                    }
                    hashmap.get(previousWord).add(word); // Add the current word as a possible next word for the previous word
                } else { // If there is no previous word (start of a sentence)
                    startingWords.add(word); // Add the current word as a starting word
                }

                if (word.endsWith(".") || word.endsWith("!") || word.endsWith("?")) { // If the word ends with a sentence-ending punctuation
                    previousWord = null; // Reset the previous word (start a new sentence)
                } else { // If the word does not end a sentence
                    previousWord = word; // Set the current word as the previous word for the next iteration
                }
            }
        }
    }

    // Method to generate text using the trained Markov chain
    public String generateText(int wordCount) {
        if (startingWords.isEmpty() || wordCount <= 0) { // If starting words list is empty or word count is invalid
            return ""; // Return an empty string
        }

        StringBuilder sb = new StringBuilder(); // Initialize a StringBuilder to store the generated text
        String currentWord = startingWords.get(r.nextInt(startingWords.size())); // Select a random starting word
        sb.append(currentWord); // Append the starting word to the StringBuilder

        int wordsGenerated = 1; // Initialize word count to 1 (for the starting word)
        while (wordsGenerated < wordCount) { // Continue generating text until the desired word count is reached
            List<String> nextWords = hashmap.get(currentWord); // Get possible next words for the current word
            if (nextWords == null || nextWords.isEmpty()) { // If there are no possible next words
                break; // Exit the loop
            }
            currentWord = nextWords.get(r.nextInt(nextWords.size())); // Select a random next word
            sb.append(" ").append(currentWord); // Append the next word to the StringBuilder
            wordsGenerated++; // Increment the word count

            if (currentWord.endsWith(".") || currentWord.endsWith("!") || currentWord.endsWith("?")) { // If the word ends a sentence
                sb.append("\n"); // Add a newline character
                if (wordsGenerated < wordCount) { // If more words are needed
                    currentWord = startingWords.get(r.nextInt(startingWords.size())); // Select a random starting word
                    sb.append(currentWord).append(" "); // Append the starting word to start a new sentence
                    wordsGenerated++; // Increment the word count
                }
            }
        }

        return sb.toString(); // Return the generated text as a string
    }
}