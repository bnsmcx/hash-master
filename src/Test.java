import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static String testHash() {
        StringBuilder output = new StringBuilder();
        Hash testHash = new Hash("098f6bcd4621d373cade4e832627b4f6");
        output.append("Testing Hash class...\nInitial toString call:\n\n\t");
        output.append(testHash.toString()).append("\n");
        output.append("\nUsing setter methods to populate instance variables...\n");
        testHash.setPassword("test");
        testHash.setPossibleHashTypes(new ArrayList<String>(Arrays.asList("MD5", "MD4", "SHA1")));
        testHash.setVerifiedHashType("MD5");
        output.append("\nShow contents of possibleHashTypes list:\n\n\t");
        output.append(testHash.possibleHashTypes);
        output.append("\n\nFinal toString call:\n\n\t");
        output.append(testHash.toString());
        return String.valueOf(output);
    }

    public static String testHashQueue() {

        // get a test file from the user
        File inputFile =new File(System.getProperty("user.dir"));;
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(inputFile);
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) inputFile = jfc.getSelectedFile();

        // create a new HashQueue with the file
        HashQueue testHashQueue = new HashQueue(inputFile);

        // call and return the toString for our test Hash Queue
        return testHashQueue.toString();
    }
}
