import java.io.IOException;

public class MainCLI {
    static String wordlist = "/home/ben/parrot/rockyou.txt";

    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
            return;
        }
        HashQueue hash = new HashQueue();
        try {
            hash.addHash(args[0]);
            hash.attack(wordlist, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(hash);
    }

    private static void printHelp() {
        // TODO update this message when ready to release
        System.out.println("Requires a hash as an argument:");
        System.out.println("\n\tjava MainCLI <hash>");
    }
}
