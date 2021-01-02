import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class HashTypeIdentifier {
    
    public static ArrayList<String> identify(String hash) {
        
        // make a list of the possible hash types found by querying hashid
        ArrayList<String> hashidGuesses = parse(hashid(hash));

        return hashidGuesses;
    }

    private static ArrayList<String> parse(String[] rawHashidGuesses) {
        ArrayList<String> cleanGuesses = new ArrayList<>();
        for (int i = 1; i < rawHashidGuesses.length; i++) {
            String guess = rawHashidGuesses[i];
            String temp = guess.split("] ")[1];
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
                rawHashidGuesses.add(sc.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawHashidGuesses.toArray(new String[0]);
    }

    public static ArrayList<String> getModes(ArrayList<String> possibleHashTypes) throws IOException {
        ArrayList<String> modesToAttempt = new ArrayList<>();
        for (String type : possibleHashTypes) {
            ArrayList<String> permutations = new ArrayList<>();
            permutations.add(type);
            permutations.add(type.replace("-", ""));
            permutations.add(type.replace("-", "").toLowerCase());
            permutations.add(type.toLowerCase().replace("sha", "sha2").toLowerCase());
            permutations.add(type.toLowerCase().replace("sha", "sha2").toUpperCase());
            permutations.add(type.toLowerCase().replace("sha", "sha3").toLowerCase());
            permutations.add(type.toLowerCase().replace("sha", "sha3").toUpperCase());
            permutations.add(type.toLowerCase());
            permutations.add(type.toUpperCase());
            for (String permutation : permutations){
                Process proc = Runtime.getRuntime().exec(
                        String.format("bash get_modes.sh %s", permutation));
                Scanner sc = new Scanner(proc.getInputStream());
                while (sc.hasNext()) {
                    String temp = sc.nextLine();
                    modesToAttempt.add(temp);
                }
            }
        }
        modesToAttempt = (ArrayList<String>) modesToAttempt.stream().distinct().collect(Collectors.toList());
        return modesToAttempt;
    }

    public static String getTypeFromMode(String mode) throws IOException {
        Process proc = Runtime.getRuntime().exec(String.format("bash get_type_from_mode.sh %s", mode));
        Scanner sc = new Scanner(proc.getInputStream());
        return sc.nextLine();
    }

    public static ArrayList<String> getAllModes() throws IOException {
        ArrayList<String> allModes = new ArrayList<>();
        Process proc = Runtime.getRuntime().exec("bash get_all_modes.sh");
        Scanner sc = new Scanner(proc.getInputStream());
        while (sc.hasNext()){
            allModes.add(sc.nextLine());
        }
        return allModes;
    }
}
