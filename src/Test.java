import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static String testHash() {
        Hash testHash = new Hash("e4ad93ca07acb8d908a3aa41e920ea4f4ef4f26e7f86cf8291c5db289780a5ae");
        testHash.crack();
        return testHash.toString();
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

        // crack the hashes
        testHashQueue.crackAll();

        // call and return the toString for our test Hash Queue
        return testHashQueue.toString();

    }
}
