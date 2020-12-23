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
        for (String guess : rawHashidGuesses) {
            System.out.println(guess);
            String temp = guess.split("] ")[1];
            System.out.println(temp);
            cleanGuesses.add(temp);
        }
        return cleanGuesses;
    }

    private static String[] hashid(String hash) {
        String[] rawHashidGuesses = new String[20];
        Runtime rt = Runtime.getRuntime();
        String command = "hashid " + hash;

        try {
            rt.exec(command);
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()) {
                for (int i =0; i < 20; i++){
                    rawHashidGuesses[i] = sc.nextLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawHashidGuesses;
    }
}
