import java.util.ArrayList;

public class Hash implements Comparable{
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

    protected void setPassword(String password) {
        this.password = password;
    }

    protected void setVerifiedHashType(String verifiedHashType) {
        this.verifiedHashType = verifiedHashType;
    }

    protected void setPossibleHashTypes(ArrayList<String> possibleHashTypes) {
        this.possibleHashTypes = possibleHashTypes;
    }

    public String toString() {
        return hash + ":" + verifiedHashType + ":" + password;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
