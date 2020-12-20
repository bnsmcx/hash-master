import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MainGUI extends JFrame implements ActionListener {

    // input panel components
    private final JLabel inputLabel = new JLabel("Enter Tree: ");
    private final JTextField inputText = new JTextField();

    // button panel components
    private final JButton makeTreeButton = new JButton("Make Tree");
    private final JButton isBalancedButton = new JButton("Is Balanced?");
    private final JButton isFullButton = new JButton("Is Full?");
    private final JButton isProperButton = new JButton("Is Proper?");
    private final JButton heightButton = new JButton("Height");
    private final JButton nodesButton = new JButton("Nodes");
    private final JButton  inorderButton = new JButton("Inorder");

    // output panel components
    private final JLabel outputLabel = new JLabel("Output: ");
    private final JTextField outputText = new JTextField();

    // constructor
    public MainGUI() {

        // frame setup
        super("Binary Tree Calculator");
        setSize(750, 160);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        JPanel inputPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        buttonPanel.setLayout(new FlowLayout());
        outputPanel.setLayout(new FlowLayout());

        // set text field sizes
        Dimension textSize = new Dimension(300, 25);
        inputText.setPreferredSize(textSize);
        outputText.setPreferredSize(textSize);

        // populate panels
        inputPanel.add(inputLabel);
        inputPanel.add(inputText);
        buttonPanel.add(makeTreeButton);
        buttonPanel.add(isBalancedButton);
        buttonPanel.add(isFullButton);
        buttonPanel.add(isProperButton);
        buttonPanel.add(heightButton);
        buttonPanel.add(nodesButton);
        buttonPanel.add(inorderButton);
        outputPanel.add(outputLabel);
        outputPanel.add(outputText);

        // add sub-panels to frame
        add(inputPanel);
        add(buttonPanel);
        add(outputPanel);

        // listen for action on all the buttons
        makeTreeButton.addActionListener(this);
        isBalancedButton.addActionListener(this);
        isFullButton.addActionListener(this);
        isProperButton.addActionListener(this);
        heightButton.addActionListener(this);
        nodesButton.addActionListener(this);
        inorderButton.addActionListener(this);

        // prevent users from editing output text box
        outputText.setEditable(false);

    } // end constructor

    // a single method to handle all button clicks
    @Override
    public void actionPerformed(ActionEvent event) {

        // determine which button was clicked
        String buttonClicked = event.getActionCommand();

        // take appropriate action based on which button was selected
        try {
            switch (buttonClicked) {
                case "Make Tree":

                    break;
                case "Is Balanced?":

                    break;
                case "Is Full?":

                    break;
                case "Is Proper?":

                    break;
                case "Height":

                    break;
                case "Nodes":

                    break;
                case "Inorder":

                    break;
            }
        } // end try

        // alert the user if they try to perform an operation without a valid tree
        catch (NullPointerException exception) {
            JOptionPane.showMessageDialog(null, "You must make a tree first");
        }

        // alert a user if no input detected
        catch (StringIndexOutOfBoundsException message) {
            JOptionPane.showMessageDialog(null, "No input detected");
        }
    } // end actionPerformed

    public static void main(String[] args) {
        MainGUI window = new MainGUI();
        window.setVisible(true);
    } // end main method
} // end Main class