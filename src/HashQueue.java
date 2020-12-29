import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HashQueue {

    protected Stack<Hash> hashes = new Stack<>();
    protected String wordlist = "/home/daisy/parrot/rockyou.txt";

    // default constructor
    public HashQueue() {

    }

    // Constructor allows input file to be passed at construction
    public HashQueue(File inputFile) throws IOException {
        addHash(inputFile);
    }

    public void magic(Hash hashObject) {
        for (String mode : hashObject.modesToAttempt) {
            if (hashObject.modesAttempted.contains(mode)) continue;
            String command = String.format("hashcat --force -m %s %s " + wordlist + "", mode, hashObject.hash);
            System.out.println(command);
            try {
                hashObject.modesAttempted.add(mode);
                Process proc = Runtime.getRuntime().exec(command);
                proc.waitFor();
                proc = Runtime.getRuntime().exec(String.format("bash /home/daisy/hashcat-GUI/check_potfile.sh %s", hashObject.hash));
                Scanner sc = new Scanner(proc.getInputStream());
                if (sc.hasNext()) {
                    hashObject.password = sc.next();
                    hashObject.verifiedHashType = HashTypeIdentifier.getTypeFromMode(mode);
                    return;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void hailMary(Hash hash) throws IOException {
        System.out.println("HAIL MARY!!!");
        hash.modesToAttempt = HashTypeIdentifier.getAllModes();
        magic(hash);
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

    protected void crackAll() throws IOException {
        for (Hash h : hashes) {
            h.crack();
        }
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

        public void crack() throws IOException {
            beingProcessed = true;
            magic(this);
            if (password == null) hailMary(this);
            System.out.println(this.toString());
            beingProcessed = false;
        }

        public String toString() {
            return "Hash:    " + hash + "\n    Hash Type:\t" + verifiedHashType + "\n    Password:\t" + password + "\n";
        }
    }
}
