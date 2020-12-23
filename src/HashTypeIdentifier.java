import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HashTypeIdentifier {
    
    public static ArrayList<String> identify(String hash) {

        // stores list of possible hash types
        ArrayList<String> possibleHashTypes = new ArrayList<>();
        
        // make a list of the possible hash types found by querying hashid
        ArrayList<String> hashidGuesses = parse(hashid(hash));

        return hashidGuesses;
    }

    private static ArrayList<String> parse(String[] rawHashidGuesses) {
        ArrayList<String> cleanGuesses = new ArrayList<>();
        for (int i = 1; i < rawHashidGuesses.length; i++) {
            String guess = rawHashidGuesses[i];
            System.out.println(guess);
            String temp = guess.split("] ")[1];
            System.out.println(temp);
            cleanGuesses.add(temp);
        }
        return cleanGuesses;
    }

    private static String[] hashid(String hash) {
        ArrayList<String> rawHashidGuesses = new ArrayList<>();
        Runtime rt = Runtime.getRuntime();
        String command = "hashid " + hash;

        try {
            Process proc = rt.exec(command);
            Scanner sc = new Scanner(proc.getInputStream());
            while (sc.hasNext()) {
                System.out.println("Test");
                rawHashidGuesses.add(sc.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawHashidGuesses.toArray(new String[0]);
    }
}
