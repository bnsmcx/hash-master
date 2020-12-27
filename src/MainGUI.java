import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

class MainGUI extends JFrame implements ActionListener {

    // hashQueue for all our hashes
    HashQueue hashQueue = new HashQueue();

    // File object for user input
    static File inputFile = new File(System.getProperty("user.dir"));

    // input panel components
    private final JButton addHashButton = new JButton("Add Hash");
    private final JButton inputFileButton = new JButton("Load Hashes From File");
    private final JTextField inputHash = new JTextField();

    // button panel components
    private final JButton magicButton = new JButton("Magic");

    // output panel components
    String[] columnNames = {"Hash", "Type", "Password"};
    String[][] data = new String[100][3];
    JTable hashTable = new JTable(data, columnNames);
    JScrollPane scrollPane= new JScrollPane(hashTable);

    // constructor
    public MainGUI() {

        // frame setup
        super("Hashcat GUI");
        setSize(1000, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        JPanel inputPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        outputPanel.setLayout(new FlowLayout());
        hashTable.setDefaultRenderer(hashTable.getColumnClass(2), new ColorRenderer());

        // set dimensions
        scrollPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()-100));
        inputHash.setPreferredSize(new Dimension(300, 25));

        // populate panels
        inputPanel.add(addHashButton);
        inputPanel.add(inputHash);
        inputPanel.add(inputFileButton);
        buttonPanel.add(magicButton);
        outputPanel.add(scrollPane);


        // add sub-panels to frame
        add(inputPanel);
        add(buttonPanel);
        add(outputPanel);

        // listen for action on input button
        inputFileButton.addActionListener(this);
        addHashButton.addActionListener(this);
        magicButton.addActionListener(this);

        // prevent users from editing output text box

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
        updatedTable.setDefaultRenderer(hashTable.getColumnClass(2), new ColorRenderer());
        hashTable.setModel(updatedTable.getModel());
    }

    public static void main(String[] args) {
        MainGUI window = new MainGUI();
        window.setVisible(true);
    } // end main method

    class ColorRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (value != null) {
                if (hashQueue.hashes.get(0).password == "maryland") setBackground(Color.GREEN);
                if (hashQueue.hashes.get(0).password == "ohio") setBackground(Color.CYAN);


            }

            return super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
        }
    }
} // end Main class

