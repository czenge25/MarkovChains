import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.println("Input file name: ");

        String inFile = s.nextLine();
        MarkovChain mc = new MarkovChain();

        mc.train(inFile);

        System.out.println("How many words?");

        Scanner s1 = new Scanner(System.in);
        int numWords = s1.nextInt();

        System.out.println("Output file name: ");
        String outFile = s.nextLine();

        String generatedText = mc.generateText(numWords);

        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(generatedText);
        }

        System.out.println("Generated text:");
        System.out.println(generatedText);

    }
}