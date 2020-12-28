import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MainGUI extends JFrame implements ActionListener {

    // hashQueue for all our hashes
    HashQueue hashQueue = new HashQueue();

    // File objects for input hashes and wordlists
    static File inputFile = new File(System.getProperty("user.dir"));
    //TODO add wordlist object

    // input panel components
    private final JButton addHashButton = new JButton("Add Hash");
    private final JButton inputFileButton = new JButton("Load Hashes From File");
    private final JTextField inputHash = new JTextField();
    private final JButton magicButton = new JButton("Magic");
    JPanel inputPanel = new JPanel();

    // output panel components
    JPanel outputPanel = new JPanel();
    String[] columnNames = {"Hash", "Type", "Password"};
    String[][] data = new String[100][3];
    JTable hashTable = new JTable(data, columnNames);
    JScrollPane scrollPane= new JScrollPane(hashTable);

    // wordlist panel components
    JPanel wordlistPanel = new JPanel();
    JButton selectWordlistButton = new JButton("Set Wordlist");
    JTextField wordlistPathText = new JTextField();

    // constructor
    public MainGUI() {

        // frame setup
        super("Hashcat GUI");
        setSize(1000, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // panel and tabbed pane config
        JTabbedPane tabbedPane = new JTabbedPane();
        inputPanel.setLayout(new FlowLayout());
        outputPanel.setLayout(new FlowLayout());
        scrollPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/2));
        inputHash.setPreferredSize(new Dimension(300, 25));
        tabbedPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/2));
        wordlistPathText.setPreferredSize(new Dimension(300, 25));


        // populate panels
        inputPanel.add(addHashButton);
        inputPanel.add(inputHash);
        inputPanel.add(inputFileButton);
        inputPanel.add(magicButton);
        outputPanel.add(scrollPane);
        wordlistPanel.add(selectWordlistButton);
        wordlistPanel.add(wordlistPathText);

        // populate tabbed top panel
        tabbedPane.add("Input", inputPanel);
        tabbedPane.add("Wordlist", wordlistPanel);


        // populate frame with top level containers
        add(tabbedPane);
        add(outputPanel);

        // listen for action on buttons
        inputFileButton.addActionListener(this);
        addHashButton.addActionListener(this);
        magicButton.addActionListener(this);
        selectWordlistButton.addActionListener(this);


    } // end constructor
    // action listener

    @Override
    public void actionPerformed(ActionEvent event) {
        // determine which button was clicked
        String buttonClicked = event.getActionCommand();

        // take appropriate action based on which button was selected
        try {
            switch (buttonClicked) {
                case "Add Hash":
                    String hash = inputHash.getText();
                    if (hash == null) break;
                    hashQueue.addHash(hash);
                    updateTable();
                    break;
                case "Load Hashes From File":
                    JFileChooser jfc = new JFileChooser();
                    jfc.setCurrentDirectory(inputFile);
                    int returnValue = jfc.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) inputFile = jfc.getSelectedFile();
                    hashQueue.addHash(inputFile);
                    updateTable();
                    break;
                case "Magic":
                    hashQueue.crackAll();
                    updateTable();
                    break;
                case "Set Wordlist":
                    JFileChooser wordlistChooser = new JFileChooser();
                    wordlistChooser.setCurrentDirectory(inputFile);
                    int rv = wordlistChooser.showOpenDialog(null);
                    if (rv == JFileChooser.APPROVE_OPTION) inputFile = wordlistChooser.getSelectedFile();
                    hashQueue.addHash(inputFile);
                    wordlistPathText.setText("bb");
                    break;
            }
        } // end try

        // alert a user if no input detected
        catch (StringIndexOutOfBoundsException message) {
            JOptionPane.showMessageDialog(null, "No input detected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // end actionPerformed

    private void updateTable() {
        data = new String[hashQueue.hashes.size()][3];
        int i = 0;
        for (Hash hash : hashQueue.hashes) {

            data[i][0] = hash.hash;
            data[i][1] = hash.verifiedHashType;
            data[i][2] = hash.password;

            i++;
        }
        JTable updatedTable = new JTable(data, columnNames);
        hashTable.setModel(updatedTable.getModel());
    }

    public static void main(String[] args) {
        MainGUI window = new MainGUI();
        window.setVisible(true);
    } // end main method
} // end Main class

