package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by ad on 2017-05-14.
 */
public class Options extends JFrame implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private JTextField logLoc = new JTextField();
    private JButton submit = new JButton("Submit");
    private JCheckBox log = new JCheckBox();
    @SuppressWarnings("rawtypes")
    private JComboBox codeMaker, codeBreaker;
    private JSlider computerDifficulty;
    private JSlider computerResponse;
    private JSlider numOfGuesses;
    private JSlider gameMode;
    private MainGUIPanel _gui;
    private boolean newGame = false;

    /**
     * The options pop up menu
     */

    Options(MainGUIPanel gui) {
        this._gui = gui;

        newGame = false;
        setSize(750, 400);
        setTitle("Options");
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

        // Fill with components
        makeContainer();
        setVisible(false);

    }

    void newGame() {
        setVisible(true);
        this.newGame = true;
        codeBreaker.setEnabled(true);
        codeMaker.setEnabled(true);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    void makeContainer() {
        String[] players = {"Human", "Computer"};

        JPanel mainLayout = new JPanel(new GridBagLayout());
        JPanel contents = new JPanel(new GridLayout(5, 4, 10, 30));

        JLabel codeBreakerLabel = new JLabel("Codebreaker:");
        contents.add(codeBreakerLabel);
        codeBreaker = new JComboBox(players);
        contents.add(codeBreaker);

        JLabel codeMakerLabel = new JLabel("Codemaker:");
        contents.add(codeMakerLabel);
        codeMaker = new JComboBox(players);
        contents.add(codeMaker);

        JLabel logLabel = new JLabel("Log");
        contents.add(logLabel);
        log.addActionListener(this);
        contents.add(log);

        JLabel logLocLabel = new JLabel("Log location:");
        contents.add(logLocLabel);
        logLoc.addKeyListener(this);
        logLoc.setText("Log.txt");
        contents.add(logLoc);

        JLabel computerLabel = new JLabel("Computer Difficulty:");
        contents.add(computerLabel);
        computerDifficulty = new JSlider(1, 3, 2);
        computerDifficulty.setPaintLabels(true);
        computerDifficulty.setPaintTicks(true);
        computerDifficulty.setSnapToTicks(true);
        computerDifficulty.setMajorTickSpacing(1);
        contents.add(computerDifficulty);

        JLabel computerResponseLabel = new JLabel("Computer Response (sec):");
        contents.add(computerResponseLabel);
        computerResponse = new JSlider(0, 30, 2);
        computerResponse.setPaintLabels(true);
        computerResponse.setPaintTicks(true);
        computerDifficulty.setSnapToTicks(true);
        computerResponse.setMajorTickSpacing(10);
        contents.add(computerResponse);

        JLabel guessesLabel = new JLabel("Number of Guesses");
        contents.add(guessesLabel);
        numOfGuesses = new JSlider(10, 20, 10);
        numOfGuesses.setPaintLabels(true);
        numOfGuesses.setPaintTicks(true);
        numOfGuesses.setSnapToTicks(true);
        numOfGuesses.setMajorTickSpacing(2);
        contents.add(numOfGuesses);

        JLabel mode = new JLabel("Game Mode");
        contents.add(mode);
        gameMode = new JSlider(1, 2, 1);
        gameMode.setPaintLabels(true);
        gameMode.setPaintTicks(true);
        gameMode.setSnapToTicks(true);
        gameMode.setMajorTickSpacing(1);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1, new JLabel("Novice"));
        labelTable.put(2, new JLabel("Expert"));
        gameMode.setLabelTable(labelTable);
        contents.add(gameMode);

        JLabel emptypLabel3 = new JLabel("");
        contents.add(emptypLabel3);

        JLabel emptyLabel4 = new JLabel("");
        contents.add(emptyLabel4);

        JLabel emptyLabel5 = new JLabel("");
        contents.add(emptyLabel5);

        submit.addActionListener(e -> {
            if (codeBreaker.getSelectedItem().toString().equals("Computer") && !newGame) {
                _gui.showFeedbackPanel();
            }
            if (newGame) {
                newGame = false;
            }
            setVisible(false);
            if (log.isSelected()) {
                File f = new File(logLoc.getText());
                int overwrite;
                if (f.exists()) {
                    overwrite = JOptionPane.showConfirmDialog(null, "File exists. OK to overwrite?",
                            "File already exists", JOptionPane.YES_NO_OPTION);
                    if (overwrite == 1)
                        return;
                }
            }

            codeBreaker.setEnabled(false);
            codeMaker.setEnabled(false);

            ArrayList<String> formInfo = new ArrayList<>();
            formInfo.add("" + codeBreaker.getSelectedItem());
            formInfo.add("" + codeMaker.getSelectedItem());
            formInfo.add("" + log.isSelected());
            formInfo.add("" + logLoc.getText());
            formInfo.add("" + computerDifficulty.getValue());
            formInfo.add("" + computerResponse.getValue());
            formInfo.add("" + numOfGuesses.getValue());
            formInfo.add("" + gameMode.getValue());

            _gui.setOptions(formInfo);

        });
        contents.add(submit);

        //Add components to main frame
        mainLayout.add(contents);
        add(mainLayout);

    }

    public void actionPerformed(ActionEvent e) {
        if (log.isSelected() && logLoc.getText().isEmpty()) {
            submit.setEnabled(false);
        } else {
            submit.setEnabled(true);
        }
    }

    public void keyPressed(KeyEvent e) {
        actionPerformed(null);
    }

    public void keyReleased(KeyEvent e) {
        actionPerformed(null);
    }

    public void keyTyped(KeyEvent e) {
        actionPerformed(null);
    }

}
