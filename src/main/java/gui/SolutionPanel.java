package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ad on 2017-05-14.
 */
public class SolutionPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    private String[] colorList;
    private JComboBox[] pegs;

    private MainGUIPanel mainPanel;
    private JPanel viewSolutionPanel;
    private JPanel inputSolutionPanel;
    private JPanel submitSolutionPanel;

    public SolutionPanel(MainGUIPanel mainPanel) {

        // Initialize the private instance variables
        this.mainPanel = mainPanel;
        this.colorList = new String[]{"RED", "BLUE", "GREEN", "YELLOW", "WHITE", "BLACK", "PURPLE"};

        // Add the panels to this board
        makeViewSolutionPanel();
        makeInputSelectionPanel();
        makeSubmitSolutionPanel();

        // Set super panel's display properties
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 5, 5));
        //setSize(300, 200);
        setSize(300, 300);
        setTitle("Solution");
        setResizable(false);

        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        // Move the window
        setLocation(x, y);

    }
    
    public void newGame() {
        setVisible(true);
        setUserInput(true);
    }

    public void newGameComputer() {
        setUserInput(false);
    }

    private void makeViewSolutionPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 25, 25, 25));
        panel.setLayout(new GridLayout(1, 4, 25, 25));

        for (int i = 0; i < 4; i++) {
            JButton button = new JButton();
            button.setEnabled(false);
            button.setBackground(Color.WHITE);
            panel.add(button);
        }

        this.add(panel);
        this.viewSolutionPanel = panel;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void makeInputSelectionPanel() {

        ImageIcon red = new ImageIcon(getClass().getResource("RED.png"));
        ImageIcon blue = new ImageIcon(getClass().getResource("BLUE.png"));
        ImageIcon green = new ImageIcon(getClass().getResource("GREEN.png"));
        ImageIcon yellow = new ImageIcon(getClass().getResource("YELLOW.png"));
        ImageIcon white = new ImageIcon(getClass().getResource("WHITE.png"));
        ImageIcon black = new ImageIcon(getClass().getResource("BLACK.png"));
        ImageIcon purple = new ImageIcon(getClass().getResource("PURPLE.png"));

        Object[] pegs = {red, blue, green, yellow, white, black, purple};

        JComboBox pegOne = new JComboBox(pegs);
        JComboBox pegTwo = new JComboBox(pegs);
        JComboBox pegThree = new JComboBox(pegs);
        JComboBox pegFour = new JComboBox(pegs);

        this.pegs = new JComboBox[4];
        this.pegs[0] = pegOne;
        this.pegs[1] = pegTwo;
        this.pegs[2] = pegThree;
        this.pegs[3] = pegFour;

        JPanel panel = new JPanel();
        panel.add(pegOne);
        panel.add(pegTwo);
        panel.add(pegThree);
        panel.add(pegFour);

        this.add(panel);
        this.inputSolutionPanel = panel;

    }

    /**
     * Creates the panel that holds the submit button.
     */
    private void makeSubmitSolutionPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 1, 5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JButton submit = new JButton("Submit");
        submit.addActionListener(new EncodeListener());
        panel.add(submit);
        this.add(panel);
        this.submitSolutionPanel = panel;
    }

    @SuppressWarnings("rawtypes")
    public List<String> getSelectedPegs() {
        List<String> selected = new ArrayList<>();
        for (JComboBox color : pegs)
            selected.add(this.colorList[color.getSelectedIndex()]);
        return selected;
    }

    public void redrawSolution(List<String> imageFiles) {
        for (int i = 0; i < imageFiles.size(); i++) {
            JButton button = (JButton) viewSolutionPanel.getComponent(i);
            try {
                Image img = ImageIO.read(getClass().getResource(imageFiles.get(i)));
                button.setIcon(new ImageIcon(img));
                button.setEnabled(true);
            } catch (Exception e) {
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void setUserInput(Boolean exp) {
        //enable the dropdowns
        for (Component colorDropbown : inputSolutionPanel.getComponents()) {
            ((JComboBox) colorDropbown).setSelectedIndex(0);
            colorDropbown.setEnabled(exp);
        }
        //enable the submit button
        for (Component submitButton : submitSolutionPanel.getComponents()) {
            submitButton.setEnabled(exp);
        }
    }

    class EncodeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mainPanel.setSolutionCode(getSelectedPegs());
            setUserInput(false);
            setVisible(false);
        }
    }

}