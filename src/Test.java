import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static String testHash() {
        Hash testHash = null;
        try {
            testHash = new Hash("84da0b929dc922d0958fdf870f546794");
            testHash.crack();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testHash.toString();
    }

    public static String testHashQueue() throws IOException {

        // get a test file from the user
        File inputFile =new File(System.getProperty("user.dir"));;
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(inputFile);
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) inputFile = jfc.getSelectedFile();

        // create a new HashQueue with the file
        HashQueue testHashQueue = new HashQueue(inputFile);

        // crack the hashes
        try {
            testHashQueue.crackAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // call and return the toString for our test Hash Queue
        return testHashQueue.toString();

    }
}
