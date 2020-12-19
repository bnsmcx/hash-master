import java.util.ArrayList;

public class MainGUI {
    public static void main(String[] args) {
        // make a test hash
        Hash testHash = new Hash("1234567890");

        // output initial construction
        System.out.println("Initial toString() call:\t" + testHash.toString());
        System.out.println("Initial possibleHashTypes:");
        System.out.println(testHash.possibleHashTypes);

        // test set methods
        System.out.println("Testing setPassword");
        testHash.setPassword("password");

        System.out.println("Testing setVerifiedHashTypes");
        testHash.setVerifiedHashType("MD5");

        System.out.println("Testing setPossibleHashTypes");
        ArrayList<String> possibleHashTypes = new ArrayList<String>();
        possibleHashTypes.add("MD5");
        possibleHashTypes.add("SHA1");
        possibleHashTypes.add("MD2");
        testHash.setPossibleHashTypes(possibleHashTypes);

        System.out.println("Final toString() call:\t" + testHash.toString());
        System.out.println("Final possibleHashTypes:\t");
        System.out.println(testHash.possibleHashTypes);
    }
}
