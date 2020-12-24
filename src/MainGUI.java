import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MainGUI extends JFrame implements ActionListener {

    // test tab components
    JPanel testPanel = new JPanel();
    JButton testHashButton = new JButton("Test Hash");
    JButton testHashQueueButton = new JButton("Test Hash Queue");
    JTextArea testOutput = new JTextArea();

    // constructor
    public MainGUI() {

        // main frame setup
        super("Hashcat GUI");
        setSize(1200, 1200);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // tabbed pane holds all swing containers within the parent JFrame
        JTabbedPane tabbedPane = new JTabbedPane();

        // add components to testPanel and add testPanel to tabbedPane
        testPanel.add(testHashButton);
        testPanel.add(testHashQueueButton);
        testPanel.add(testOutput);
        testOutput.setPreferredSize(new Dimension(1150, 1150));
        tabbedPane.setPreferredSize(new Dimension(1200, 1200));
        tabbedPane.addTab("Test", testPanel);

        // add tabbedPane to frame
        add(tabbedPane);

        // set action listeners
        testHashButton.addActionListener(this);
        testHashQueueButton.addActionListener(this);

    } // end constructor

    // a single method to handle all button clicks
    @Override
    public void actionPerformed(ActionEvent event) {

        // determine which button was clicked
        String buttonClicked = event.getActionCommand();

        // take appropriate action based on which button was selected
        try {
            switch (buttonClicked) {
                case "Test Hash":
                    testOutput.setText(Test.testHash());

                    break;
                case "Test Hash Queue":
                    testOutput.setText(Test.testHashQueue());
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