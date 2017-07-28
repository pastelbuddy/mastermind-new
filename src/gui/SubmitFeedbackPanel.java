package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SubmitFeedbackPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private MainGUIPanel gui;
    private final String[] pegs = {"NONE", "BLACK", "WHITE"};
    @SuppressWarnings("rawtypes")
    private JComboBox pegOne;
    @SuppressWarnings("rawtypes")
    private JComboBox pegTwo;
    @SuppressWarnings("rawtypes")
    private JComboBox pegThree;
    @SuppressWarnings("rawtypes")
    private JComboBox pegFour;

    public JPanel makeFeedPanel(MainGUIPanel guiPanel) {
        gui = guiPanel;

        JPanel feedBackPanel = new JPanel();

        feedBackPanel.setLayout(new BorderLayout());

        feedBackPanel.setBorder(new EmptyBorder(10, 25, 10, 25));
        feedBackPanel.add(submitPanel(), BorderLayout.EAST);
        feedBackPanel.add(guessSelectPanel(), BorderLayout.CENTER);
        feedBackPanel.add(undoPanel(), BorderLayout.WEST);

        return feedBackPanel;
    }

    private JPanel undoPanel() {
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

    private JPanel submitPanel() {
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

    private List<String> getSelectedPegs() {
        List<String> selected = new ArrayList<>();

        selected.add(pegs[pegOne.getSelectedIndex()]);
        selected.add(pegs[pegTwo.getSelectedIndex()]);
        selected.add(pegs[pegThree.getSelectedIndex()]);
        selected.add(pegs[pegFour.getSelectedIndex()]);

        return selected;
    }

    private void reset() {
        pegOne.setSelectedIndex(0);
        pegTwo.setSelectedIndex(0);
        pegThree.setSelectedIndex(0);
        pegFour.setSelectedIndex(0);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private JPanel guessSelectPanel() {
        ImageIcon none = new ImageIcon(getClass().getResource("NONE.png"));
        ImageIcon black = new ImageIcon(getClass().getResource("FEEDBK_BLACK.png"));
        ImageIcon white = new ImageIcon(getClass().getResource("FEEDBK_WHITE.png"));

        Object[] pegs = {none, black, white};

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

    class SubmitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            gui.addFeedback(getSelectedPegs());
            reset();
            //gui.nextCard();
        }
    }

    class UndoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            gui.undo();
            reset();
        }
    }
}
