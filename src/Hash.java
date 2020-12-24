import java.util.ArrayList;

public class Hash {
    String hash;
    String password;
    String verifiedHashType;
    ArrayList<String> possibleHashTypes;
    ArrayList<String> modesToAttempt;

    protected Hash(String hash) {
        this.hash = hash;
        this.possibleHashTypes = HashTypeIdentifier.identify(hash);
        this.modesToAttempt = HashTypeIdentifier.getModes(possibleHashTypes);

    }

    public void crack() {
        HashcatCommand.magic(this);
        System.out.println(this.toString());
    }

    public String toString() {
        return "Hash:    " + hash + "\n    Hash Type:\t" + verifiedHashType + "\n    Password:\t" + password + "\n";
    }
}
