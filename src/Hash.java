import java.io.IOException;
import java.util.ArrayList;

public class Hash {
    Boolean currentlyAttemptingCrack = false;
    String hash;
    String password;
    String verifiedHashType;
    ArrayList<String> possibleHashTypes;
    ArrayList<String> modesToAttempt;

    protected Hash(String hash) throws IOException {
        this.hash = hash;
        this.possibleHashTypes = HashTypeIdentifier.identify(hash);
        this.modesToAttempt = HashTypeIdentifier.getModes(possibleHashTypes);

    }

    public void crack() throws IOException {
        currentlyAttemptingCrack = true;
        HashcatCommand.magic(this);
        if (password == null) HashcatCommand.hailMary(this);
        System.out.println(this.toString());
        currentlyAttemptingCrack = false;
    }

    public String toString() {
        return "Hash:    " + hash + "\n    Hash Type:\t" + verifiedHashType + "\n    Password:\t" + password + "\n";
    }
}
