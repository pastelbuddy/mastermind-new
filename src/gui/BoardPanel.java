package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class BoardPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String NONE = "NONE.png";
    private JPanel guessPanel;
    private int gameRows;
    private int gamePegSize;


    public BoardPanel(Integer gameRows, Integer gamePegSize) {
        this.gameRows = gameRows;
        this.gamePegSize = gamePegSize;

        //Set the drawing properties for the super panel.
        this.setLayout(new GridLayout(1, 1));
        this.setBorder(new EmptyBorder(10, 25, 50, 9));

        // Make a sub-panel to display guesses.
        drawInitialGuesses();

        this.add(guessPanel);
    }

    private void drawInitialGuesses() {
        // reset the guess panel and guesses.
        guessPanel = new JPanel();

        makeDefaultButtons();

        // set the guess panel's drawing properties.
        guessPanel.setBorder(new EmptyBorder(10, 25, 25, 25));
        guessPanel.setLayout(new GridLayout(this.gameRows, this.gamePegSize, 25, 5));
    }

    private void makeDefaultButtons() {
        ImageIcon blank = new ImageIcon(getClass().getResource(NONE));

        // make all "default" buttons.
        for (int i = 0; i < this.gameRows; i++) {
            for (int j = 0; j < this.gamePegSize; j++) {
                JButton button = new JButton();
                button.setBackground(Color.white);
                button.setIcon(blank);
                guessPanel.add(button);
            }
        }
    }

    public void makeNewBoard() {
        guessPanel.removeAll();

        makeDefaultButtons();

        guessPanel.setLayout(new GridLayout(this.gameRows, this.gamePegSize, 25, 5));
        guessPanel.repaint();
        guessPanel.setVisible(false);
        guessPanel.setVisible(true);
        guessPanel.validate();
    }

    public void redrawGuesses(List<String> imageFiles) {
        for (int i = 0; i < imageFiles.size(); i++) {
            JButton button = (JButton) guessPanel.getComponent(i);
            try {
                Image img = ImageIO.read(getClass().getResource(imageFiles.get(i)));
                button.setIcon(new ImageIcon(img));
                button.setEnabled(true);
            } catch (Exception ignored) {
            }
        }

        ImageIcon blank = new ImageIcon(getClass().getResource(NONE));

        for (int j = imageFiles.size(); j < this.gameRows * this.gamePegSize; j++) {
            JButton button = (JButton) guessPanel.getComponent(j);
            try {
                button.setIcon(blank);
                button.setEnabled(true);
            } catch (Exception ignored) {
            }
        }
        validate();
    }

    public void updateGameRows(int gameRows) {
        this.gameRows = gameRows;
        makeNewBoard();
    }

    public void updatePegSize(int gamePegSize) {
        this.gamePegSize = gamePegSize;
        makeNewBoard();
    }

}
