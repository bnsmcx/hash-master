import java.io.*;
import java.util.*;

public class HashQueue {

    protected ArrayList<Hash> hashes = new ArrayList<>();
    protected String wordlist = "";
    private String rule = "";

    // default constructor
    public HashQueue() {

    }

    // Constructor allows input file to be passed at construction
    public HashQueue(File inputFile) throws IOException {
        addHash(inputFile);
    }

    public void hailMary(String wordlist, String rule) throws IOException {
        System.out.println("HAIL MARY!!!");
        for (Hash hash : hashes) hash.modesToAttempt = HashTypeIdentifier.getAllModes();
        attack(wordlist, rule);
    }

    // Creates and enqueues a single HashQueue.Hash object when passed a hash value as a String
    protected Runnable addHash(String hash) throws IOException {
        this.hashes.add(new Hash(hash));
        return null;
    }

    // iterates through an input file of hashes and calls addHash() for each provided hash
    protected void addHash(File hashes) throws IOException {
        Scanner sc = new Scanner(hashes);
        while (sc.hasNext()) addHash(sc.next());
    }

    // toString() iterates through queue and returns the info of all the hashes
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Hash hash : this.hashes) {
            sb.append(hash.toString());
            sb.append("\n");
        }
        return String.valueOf(sb);
    }

    public void attack(String wordlist, String rule) throws FileNotFoundException {
        this.wordlist = wordlist;
        this.rule = rule;

        // confirm wordlist exists, forces exception
        Scanner testWordlist = new Scanner(new File(wordlist));
        if (!testWordlist.hasNext()) throw new FileNotFoundException();
        if (rule.length() > 0) rule = " -r " + rule;

        // reset modesAttempted in case this is an attack with new parameters
        for (Hash h : hashes) h.modesAttempted.clear();

        for (Hash currentHash: hashes) {
            for (String mode : currentHash.modesToAttempt) {
                if (currentHash.modesAttempted.contains(mode)) continue;
                String command = String.format("hashcat --force -m %s%s %s " + wordlist + "", mode, rule, currentHash.hash);
                System.out.println(command);
                try {
                    currentHash.modesAttempted.add(mode);
                    Process proc = Runtime.getRuntime().exec(command);
                    proc.waitFor();
                    proc = Runtime.getRuntime().exec(String.format("bash check_potfile.sh %s", currentHash.hash));
                    Scanner sc = new Scanner(proc.getInputStream());
                    if (sc.hasNext()) {
                        currentHash.password = sc.next();
                        currentHash.verifiedHashType = HashTypeIdentifier.getTypeFromMode(mode);
                        break;
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class Hash {
        Boolean beingProcessed = false;
        String hash;
        String password;
        String verifiedHashType;
        ArrayList<String> possibleHashTypes;
        ArrayList<String> modesToAttempt;
        ArrayList<String> modesAttempted = new ArrayList<>();

        protected Hash(String hash) throws IOException {
            this.hash = hash;
            this.possibleHashTypes = HashTypeIdentifier.identify(hash);
            this.modesToAttempt = HashTypeIdentifier.getModes(possibleHashTypes);
        }

        public String toString() {
            return hash + ":" + verifiedHashType + ":" + password + "\n";
        }
    }
}
