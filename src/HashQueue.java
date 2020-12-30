import java.io.*;
import java.util.*;

public class HashQueue {

    protected Stack<Hash> hashes = new Stack<>();
    protected String wordlist = "";
    private String rule = "";

    // default constructor
    public HashQueue() {

    }

    // Constructor allows input file to be passed at construction
    public HashQueue(File inputFile) throws IOException {
        addHash(inputFile);
    }

//    public void hailMary(Hash hash) throws IOException {
//        System.out.println("HAIL MARY!!!");
//        hash.modesToAttempt = HashTypeIdentifier.getAllModes();
//        attack(hash);
//    }

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

        for (Hash hash : this.hashes) {
            System.out.println("!");
            for (String mode : hash.modesToAttempt) {
                if (hash.modesAttempted.contains(mode)) continue;
                if (rule.length() > 0) rule = " -r " + rule;
                String command = String.format("hashcat --force -m %s%s %s " + wordlist + "", mode, rule, hash.hash);
                System.out.println(command);
                try {
                    hash.modesAttempted.add(mode);
                    Process proc = Runtime.getRuntime().exec(command);
                    proc.waitFor();
                    proc = Runtime.getRuntime().exec(String.format("bash /home/daisy/hashcat-GUI/check_potfile.sh %s", hash.hash));
                    Scanner sc = new Scanner(proc.getInputStream());
                    if (sc.hasNext()) {
                        hash.password = sc.next();
                        hash.verifiedHashType = HashTypeIdentifier.getTypeFromMode(mode);
                        return;
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
            return "Hash:    " + hash + "\n    Hash Type:\t" + verifiedHashType + "\n    Password:\t" + password + "\n";
        }
    }
}
