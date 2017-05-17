package gui;

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
public class SubmitGuessPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    public MainGUIPanel gui;
    @SuppressWarnings("rawtypes")
    private JComboBox pegOne;
    @SuppressWarnings("rawtypes")
    private JComboBox pegTwo;
    @SuppressWarnings("rawtypes")
    private JComboBox pegThree;
    @SuppressWarnings("rawtypes")
    private JComboBox pegFour;
    private String[] colors = {"RED", "BLUE", "GREEN", "YELLOW", "WHITE", "BLACK", "PURPLE"};

    public JPanel makeGuessPanel(MainGUIPanel guiPanel) {
        gui = guiPanel;

        JPanel guessPanel = new JPanel();

        guessPanel.setLayout(new BorderLayout());

        guessPanel.setBorder(new EmptyBorder(10, 25, 10, 25));
        guessPanel.add(submitPanel(), BorderLayout.EAST);
        guessPanel.add(guessSelectPanel(), BorderLayout.CENTER);
        guessPanel.add(undoPanel(), BorderLayout.WEST);


        return guessPanel;
    }

    public JPanel undoPanel() {
        JPanel undoPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        undoPanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridLayout(1, 1, 30, 30));

        JButton undo = new JButton("Undo");
        undo.addActionListener(new UndoListener());

        buttonPanel.add(undo);
        undoPanel.add(buttonPanel);

        return undoPanel;
    }

    public JPanel submitPanel() {
        JPanel submitPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        submitPanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridLayout(1, 1, 30, 30));


        JButton submit = new JButton("Submit");
        submit.addActionListener(new SubmitListener());

        buttonPanel.add(submit);
        submitPanel.add(buttonPanel);

        return submitPanel;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public JPanel guessSelectPanel() {
        ImageIcon red = new ImageIcon(getClass().getResource("RED.png"));
        ImageIcon blue = new ImageIcon(getClass().getResource("BLUE.png"));
        ImageIcon green = new ImageIcon(getClass().getResource("GREEN.png"));
        ImageIcon yellow = new ImageIcon(getClass().getResource("YELLOW.png"));
        ImageIcon white = new ImageIcon(getClass().getResource("WHITE.png"));
        ImageIcon black = new ImageIcon(getClass().getResource("BLACK.png"));
        ImageIcon purple = new ImageIcon(getClass().getResource("PURPLE.png"));

        Object[] pegs = {red, blue, green, yellow, white, black, purple};

        JPanel selection = new JPanel();

        pegOne = new JComboBox(pegs);
        pegTwo = new JComboBox(pegs);
        pegThree = new JComboBox(pegs);
        pegFour = new JComboBox(pegs);

        selection.add(pegOne);
        selection.add(pegTwo);
        selection.add(pegThree);
        selection.add(pegFour);

        return selection;
    }

    public List<String> getSelectedPegs() {
        List<String> selected = new ArrayList<>();

        selected.add(colors[pegOne.getSelectedIndex()]);
        selected.add(colors[pegTwo.getSelectedIndex()]);
        selected.add(colors[pegThree.getSelectedIndex()]);
        selected.add(colors[pegFour.getSelectedIndex()]);

        return selected;
    }

    public void reset() {
        pegOne.setSelectedIndex(0);
        pegTwo.setSelectedIndex(0);
        pegThree.setSelectedIndex(0);
        pegFour.setSelectedIndex(0);
    }

    class SubmitListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            gui.addGuess(getSelectedPegs());
            reset();
        }
    }

    class UndoListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            gui.undo();
            reset();
        }
    }

}
