import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HashQueue {

    private PriorityQueue<Hash> hashes = new PriorityQueue<>();

    // Creates and enqueues a single Hash object when passed a hash value as a String
    protected void addHash(String hash) {
        this.hashes.add(new Hash(hash));
    }

    // iterates through an input file of hashes and calls addHash() for each provided hash
    protected void addHash(File hashes) {
        try {
            Scanner scanner = new Scanner(hashes);
            while (scanner.hasNext()) {
                addHash(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
}
