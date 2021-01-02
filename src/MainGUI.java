import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.*;

class MainGUI extends JFrame implements ActionListener {

    // hashQueue for all our hashes
    HashQueue hashQueue = new HashQueue();

    // File objects for input hashes and wordlists
    static File inputFile = new File(System.getProperty("user.dir"));
    static File wordlist = new File("/usr/share/wordlists/rockyou.txt");
    static String cewlOutputPath = "";
    static String rulePath = "";

    // inputPanel components
    private final JButton addHashButton = new JButton("Add Hash");
    private final JButton inputFileButton = new JButton("Load Hashes From File");
    private final JButton resetModesButton = new JButton("Reset Modes");
    private final JTextField inputHash = new JTextField();
    private final JButton magicButton = new JButton("Magic");
    private final JButton magicInfoButtion = new JButton("?");
    private final JButton hailMaryButton = new JButton("Hail Mary");
    JPanel inputPanel = new JPanel();

    // outputPanel components
    JPanel outputPanel = new JPanel();
    String[] columnNames = {"Hash", "Type", "Password"};
    String[][] data = new String[100][3];
    JTable hashTable = new JTable(data, columnNames);
    JScrollPane scrollPane= new JScrollPane(hashTable);

    // wordlistPanel components
    JPanel wordlistPanel = new JPanel();
    JButton selectWordlistButton = new JButton("Set Wordlist");
    JTextField wordlistPathText = new JTextField(wordlist.getAbsolutePath());
    JButton customWordlistButton = new JButton("Generate Wordlist with CeWL");

    // maskPanel components
    JPanel maskPanel = new JPanel();
    JButton createRuleButton = new JButton("Create Rule");
    Dimension textDimensionLong = new Dimension(300, 25);
    Dimension textDimensionShort = new Dimension(150, 25);
    JButton setRuleButton = new JButton("Set Rule");
    JTextField rulePathText = new JTextField();
    JButton clearRuleButton = new JButton("Clear Rule");

    // attackPanel components
    JPanel attackPanel = new JPanel();
    JLabel commandLabel = new JLabel("Command to execute:");
    JTextField commandText = new JTextField();
    JButton attackButton = new JButton("Attack");

    // constructor
    public MainGUI() {

        // frame setup
        super("hash-master 0.1.0");
        setSize(1000, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // panel and tabbed pane config
        JTabbedPane tabbedPane = new JTabbedPane();
        inputPanel.setLayout(new FlowLayout());
        outputPanel.setLayout(new FlowLayout());
        maskPanel.setLayout(new FlowLayout());
        scrollPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/7 * 5));
        inputHash.setPreferredSize(textDimensionLong);
        tabbedPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/6));
        wordlistPathText.setPreferredSize(textDimensionLong);
        rulePathText.setPreferredSize(textDimensionLong);
        commandText.setPreferredSize(new Dimension(650, 25));
        commandText.setEditable(false);
        updateAttack();

        // populate panels
        inputPanel.add(addHashButton);
        inputPanel.add(inputHash);
        inputPanel.add(inputFileButton);
        inputPanel.add(resetModesButton);
        inputPanel.add(magicButton);
        inputPanel.add(hailMaryButton);
        inputPanel.add(magicInfoButtion);
        outputPanel.add(scrollPane);
        wordlistPanel.add(selectWordlistButton);
        wordlistPanel.add(wordlistPathText);
        wordlistPanel.add(customWordlistButton);
        maskPanel.add(clearRuleButton);
        maskPanel.add(setRuleButton);
        maskPanel.add(rulePathText);
        maskPanel.add(createRuleButton);
        attackPanel.add(commandLabel);
        attackPanel.add(commandText);
        attackPanel.add(attackButton);

        // populate tabbed top panel
        tabbedPane.add("Input Hashes", inputPanel);
        tabbedPane.add("Choose Wordlist", wordlistPanel);
        tabbedPane.add("Configure Masks", maskPanel);
        tabbedPane.add("Attack", attackPanel);

        // populate frame with top level containers
        add(tabbedPane);
        add(outputPanel);

        // listen for action
        inputFileButton.addActionListener(this);
        addHashButton.addActionListener(this);
        magicButton.addActionListener(this);
        selectWordlistButton.addActionListener(this);
        customWordlistButton.addActionListener(this);
        magicInfoButtion.addActionListener(this);
        createRuleButton.addActionListener(this);
        setRuleButton.addActionListener(this);
        attackButton.addActionListener(this);
        clearRuleButton.addActionListener(this);
        hailMaryButton.addActionListener(this);
        resetModesButton.addActionListener(this);

    } // end constructor
    // action listener

    @Override
    public void actionPerformed(ActionEvent event) {
        // determine which button was clicked
        String buttonClicked = event.getActionCommand();

        // take appropriate action based on which button was selected
        try {
            switch (buttonClicked) {
                case "Reset Modes":
                    for (HashQueue.Hash h : hashQueue.hashes) h.possibleHashTypes = HashTypeIdentifier.identify(h.hash);
                    break;
                case "Hail Mary":
                    hashQueue.hailMary();
                    attack();
                    break;
                case "Add Hash":
                    String hash = inputHash.getText();
                    if (hash.length() > 0) {
                        hashQueue.addHash(hash);
                    }
                    inputHash.setText("");
                    break;
                case "Load Hashes From File":
                    JFileChooser jfc = new JFileChooser();
                    jfc.setCurrentDirectory(inputFile);
                    int returnValue = jfc.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) inputFile = jfc.getSelectedFile();
                    hashQueue.addHash(inputFile);
                    break;
                case "Magic":
                    attack();
                    break;
                case "Set Wordlist":
                    JFileChooser wordlistChooser = new JFileChooser();
                    wordlistChooser.setCurrentDirectory(wordlist);
                    int rv = wordlistChooser.showOpenDialog(null);
                    if (rv == JFileChooser.APPROVE_OPTION) wordlist = wordlistChooser.getSelectedFile();
                    wordlistPathText.setText(wordlist.getAbsolutePath());
                    hashQueue.wordlist = wordlist.getAbsolutePath();
                    updateAttack();
                    break;
                case "Set Rule":
                    File rule = new File(System.getProperty("user.dir"));
                    JFileChooser ruleChooser = new JFileChooser();
                    ruleChooser.setCurrentDirectory(rule);
                    int ruleVariable = ruleChooser.showOpenDialog(null);
                    if (ruleVariable == JFileChooser.APPROVE_OPTION) rule = ruleChooser.getSelectedFile();
                    rulePath = rule.getAbsolutePath();
                    rulePathText.setText(rulePath);
                    updateAttack();
                    break;
                case "Generate Wordlist with CeWL":
                    JFrame cewlDialog = new JFrame("CeWL");
                    JButton run = new JButton("Run");
                    JLabel urlLabel = new JLabel("Target URL:");
                    JTextField targetUrl = new JTextField();
                    JLabel depthLabel = new JLabel("Depth to spider:");
                    JTextField depth = new JTextField();
                    JLabel outputLocation = new JLabel("CeWL output not set.");
                    JButton save = new JButton("Set CeWL output");
                    targetUrl.setPreferredSize(textDimensionShort);
                    depth.setPreferredSize(textDimensionShort);
                    cewlDialog.setSize(200, 250);
                    cewlDialog.setLayout(new FlowLayout());
                    cewlDialog.setResizable(true);
                    cewlDialog.add(urlLabel);
                    cewlDialog.add(targetUrl);
                    cewlDialog.add(depthLabel);
                    cewlDialog.add(depth);
                    cewlDialog.add(outputLocation);
                    cewlDialog.add(save);
                    cewlDialog.add(run);
                    cewlDialog.setLocationRelativeTo(null);
                    cewlDialog.setVisible(true);

                    save.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFileChooser fileChooser = new JFileChooser();
                            int option = fileChooser.showSaveDialog(null);
                            if(option == JFileChooser.APPROVE_OPTION) {
                                File file = fileChooser.getSelectedFile();
                                outputLocation.setText(file.getName());
                                cewlOutputPath = file.getAbsolutePath();
                            }
                        }
                    });

                    run.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            cewl(depth.getText(), targetUrl.getText(), cewlOutputPath);
                            cewlDialog.dispose();
                        }
                    });
                    break;
                case "?":
                    JOptionPane.showMessageDialog(null,
                                    "During import each hash is passed through 'hashid' and a list of\n" +
                                            "possible hash types is associated with the hash.\n" +
                                            "\n" +
                                            "Clicking the 'Magic' button will perform a default dictionary attack\n" +
                                            "against all the provided hashes using the list of possible hashes.\n" +
                                            "It is great for catching low hanging fruit.\n "+
                                            "\n" +
                                            "The 'Hail Mary' button will test the given hash or hashes using \n"+
                                            "every possible hashcat mode.  This should only be run against short\n" +
                                            "lists because it can obviously take a long time to run.  It has, however\n" +
                                            "proven to be very useful for catching hashes that defy 'hashid' identification.\n" +
                                            "\n" +
                                            "Please note that using the 'Hail Mary' button will set the possible hash types\n" +
                                            "for each hash to every possible hashcat mode.  This could be frustrating\n" +
                                            "if you are proceeding with custom wordlist or mask attacks after attempting\n" +
                                            "a Hail Mary.  Always hit the 'Reset Modes' button after a Hail Mary.");
                    break;
                case "Attack":
                    System.out.println(rulePath);
                    attack();
                    break;
                case "Clear Rule":
                    rulePath = "";
                    rulePathText.setText(rulePath);
                    updateAttack();
                    break;
                case "Create Rule":
                    JFrame createRuleFrame = new JFrame("Create Rule");
                    createRuleFrame.setSize(375,200);
                    createRuleFrame.setResizable(true);
                    JButton maskInfoButton = new JButton(" ? ");
                    JButton create = new JButton("Create");
                    JLabel prefixLabel = new JLabel("Prefix:");
                    JLabel postfixLabel = new JLabel("Postfix:");
                    JTextField prefixField = new JTextField("?d");
                    JTextField postfixField = new JTextField("?d");
                    prefixField.setPreferredSize(textDimensionLong);
                    postfixField.setPreferredSize(textDimensionLong);
                    JLabel capitalizationLabel = new JLabel("Manipulate Capitalization:");
                    JComboBox<String> capitalizationCombo = new JComboBox<String>(new String[]{"-", "All Uppercase",
                            "All Lowercase", "Capital first, lower rest", "Lower first, Capital rest", "Toggle Capitalization"});
                    maskInfoButton.addActionListener(this);
                    createRuleFrame.setLayout(new FlowLayout());
                    createRuleFrame.add(prefixLabel);
                    createRuleFrame.add(prefixField);
                    createRuleFrame.add(postfixLabel);
                    createRuleFrame.add(postfixField);
                    createRuleFrame.add(capitalizationLabel);
                    createRuleFrame.add(capitalizationCombo);
                    createRuleFrame.add(create);
                    createRuleFrame.add(maskInfoButton);
                    createRuleFrame.setLocationRelativeTo(null);
                    createRuleFrame.setVisible(true);

                    create.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFileChooser fileChooser = new JFileChooser();
                            int option = fileChooser.showSaveDialog(null);
                            if(option == JFileChooser.APPROVE_OPTION) {
                               rulePath = fileChooser.getSelectedFile().getAbsolutePath();
                            }
                            maskProcessor(prefixField.getText(), postfixField.getText(),
                                    (String) capitalizationCombo.getSelectedItem(), rulePath);
                            updateAttack();
                            createRuleFrame.dispose();
                        }
                    });

                    maskInfoButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null,
                                    "Character sets are referenced with a '?'\n" +
                                            "i.e. upper case alpha characters are '?u'\n" +
                                            "\n" +
                                            "\n" +
                                            "Built-in charsets:\n" +
                                            "\n" +
                                            "  ?l = abcdefghijklmnopqrstuvwxyz\n" +
                                            "  ?u = ABCDEFGHIJKLMNOPQRSTUVWXYZ\n" +
                                            "  ?d = 0123456789\n" +
                                            "  ?s =  !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\n" +
                                            "  ?a = ?l?u?d?s\n" +
                                            "  ?b = 0x00 - 0xff\n");
                        }
                    });
                    break;
                default:
                    updateAttack();
            }
        } // end try

        // alert a user if no input detected
        catch (StringIndexOutOfBoundsException message) {
            JOptionPane.showMessageDialog(null, "No input detected");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No input detected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // end actionPerformed

    private void updateAttack() {
        String rule = "";
        if (rulePath.length() > 0) rule = " -r " + rulePath;
        String command = String.format("hashcat --potfile-path=potfile -m <mode>%s <hash> " + wordlist, rule);
        commandText.setText(command);
    }

    private void attack() {
        if (hashQueue.hashes.size() < 1) {
            JOptionPane.showMessageDialog(null, "No hashes to crack.");
            return;
        }
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(new Runnable() {
            public void run() {
                try {
                    hashQueue.attack(wordlist.getAbsolutePath(), rulePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void maskProcessor(String prefix, String postfix, String capitalization, String rulePath) {
        switch (capitalization) {
            case "-":
                capitalization = "";
                break;
            case "All Uppercase":
                capitalization = "u";
                break;
            case "All Lowercase":
                capitalization = "l";
                break;
            case "Capital first, lower rest":
                capitalization = "c";
                break;
            case "Lower first, Capital rest":
                capitalization = "C";
                break;
            case "Toggle Capitalization":
                capitalization = "t";
                break;
        }
        if (prefix != null) {
            String[] pre = prefix.split("\\?");
            prefix = "";
            for (String s : pre) {
                if (s.length() < 1) continue;
                prefix += "^?" + s.strip() + " ";
            }
        }
        if (postfix != null) {
            String[] post = postfix.split("\\?");
            postfix = "";
            for (String s : post) {
                if (s.length() < 1) continue;
                postfix += "$?" + s.strip() + " ";
            }
        }
        String[] command = {"mp64", capitalization + " " + prefix + postfix, "-o", rulePath};
        rulePathText.setText(rulePath);
        try {
            Process proc = Runtime.getRuntime().exec(command);
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void cewl(String depth, String url, String cewlOutputPath) {
        url = url.replace("http://", "");
        url = url.replace("https://", "");
        String command = String.format("cewl -d  %s -w %s https://%s", depth, cewlOutputPath, url);
        try {
            Process proc = Runtime.getRuntime().exec(command);
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        wordlist = new File(cewlOutputPath);
        wordlistPathText.setText(wordlist.getAbsolutePath());
        updateAttack();
    }

    private void updateTable() {

        data = new String[hashQueue.hashes.size()][3];
        int i = 0;
        for (HashQueue.Hash hash : hashQueue.hashes) {

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
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(new Runnable() {
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(500);
                        window.updateTable();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    } // end main method
} // end Main class
