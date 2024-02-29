/*
CZ
2/29/24
HK
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.println("Input file name: "); // Prompt for input file name

        String inFile = s.nextLine(); // Reads input file name from user
        MarkovChain mc = new MarkovChain(); // Creates a new MarkovChain object

        mc.train(inFile); // Trains the Markov chain using the input file

        System.out.println("How many words?"); // Prompts for the number of words to generate

        Scanner s1 = new Scanner(System.in); // New scanner because of line skipping error with Scanner class
        int numWords = s1.nextInt(); // Reads the number of words from user

        System.out.println("Output file name: "); // Prompt for output file name
        String outFile = s.nextLine(); // Reads output file name from user

        String generatedText = mc.generateText(numWords); // Generates text using the trained Markov chain

        try (PrintWriter pw = new PrintWriter(outFile)) { // Try-with-resources to automatically close PrintWriter
            pw.println(generatedText); // Writes generated text to output file
        }

        System.out.println("Generated text:"); // Prints header for generated text
        System.out.println(generatedText); // Prints generated text
    }
}
