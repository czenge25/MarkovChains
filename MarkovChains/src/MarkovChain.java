import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class MarkovChain {

    private final HashMap<String, ArrayList<String>> hashmap = new HashMap<>();
    private final List<String> startingWords = new ArrayList<>();
    private final Random r = new Random();

    public void train(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        String previousWord = null;

        for (String line : lines) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                if (word.isEmpty()) {
                    continue;
                }

                if (previousWord != null) {
                    if (!hashmap.containsKey(previousWord)) {
                        hashmap.put(previousWord, new ArrayList<>());
                    }
                    hashmap.get(previousWord).add(word);
                } else {
                    startingWords.add(word);
                }

                if (word.endsWith(".") || word.endsWith("!") || word.endsWith("?")) {
                    previousWord = null;
                } else {
                    previousWord = word;
                }
            }
        }
    }

    public String generateText(int wordCount) {
        if (startingWords.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        String currentWord = startingWords.get(r.nextInt(startingWords.size()));
        sb.append(currentWord);

        for (int i = 1; i <= wordCount; i++) {
            List<String> nextWords = hashmap.get(currentWord);
            System.out.println(i);
            if (nextWords == null || nextWords.isEmpty()) {
                break;
            }
            currentWord = nextWords.get(r.nextInt(nextWords.size()));
            sb.append(" ").append(currentWord);
        }

        return sb.toString();
    }



}
