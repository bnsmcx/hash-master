import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class HashQueue {

    private Stack<Hash> hashes = new Stack<>();

    // Constructor allows input file to be passed at construction
    public HashQueue(File inputFile) throws IOException {
        addHash(inputFile);
    }

    // Creates and enqueues a single Hash object when passed a hash value as a String
    protected void addHash(String hash) throws IOException {
        this.hashes.add(new Hash(hash));
    }

    // iterates through an input file of hashes and calls addHash() for each provided hash
    protected void addHash(File hashes) throws IOException {
        try {
            Scanner scanner = new Scanner(hashes);
            while (scanner.hasNext()) {
                addHash(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void crackAll() throws IOException {
        for (Hash h : hashes) h.crack();
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
