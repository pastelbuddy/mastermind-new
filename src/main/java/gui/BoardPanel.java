package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Created by ad on 2017-05-14.
 */
class BoardPanel extends JPanel {

    private static final long serialVeriosnUID = 1L;
    private static final String NONE = "NONE.png";
    private JPanel guessPanel;
    private int gameRows;
    private int gamePegSize;

    BoardPanel(Integer gameRows, Integer gamePegSize) {
        this.gameRows = gameRows;
        this.gamePegSize = gamePegSize;

        this.setLayout(new GridLayout(1, 1));
        this.setBorder(new EmptyBorder(10, 25, 50, 9));

        drawInitialGuesses();
        this.add(guessPanel);
    }

    private void drawInitialGuesses() {
        // reset the guess panel and guesses
        guessPanel = new JPanel();

        makeDefaultButtons();

        guessPanel.setLayout(new GridLayout(gameRows, gamePegSize, 25, 5));
        guessPanel.setBorder(new EmptyBorder(10, 25, 25, 25));
    }

    private void makeDefaultButtons() {
        ImageIcon blank = new ImageIcon(getClass().getResource(NONE));

        //make all "default" buttons
        for (int i = 0; i < gameRows; i++) {
            for (int j = 0; j < gamePegSize; j++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                button.setIcon(blank);
                guessPanel.add(button);
            }
        }
    }

    void makeNewBoard() {
        guessPanel.removeAll();

        makeDefaultButtons();

        guessPanel.setLayout(new GridLayout(gameRows, gamePegSize, 25, 5));
        guessPanel.repaint();
        guessPanel.setVisible(false);
        guessPanel.setVisible(true);
        guessPanel.validate();
    }

    void redrawGuesses(List<String> imageFiles) {
        for (int i = 0; i < imageFiles.size(); i++) {
            JButton button = (JButton) guessPanel.getComponent(i);
            try {
                Image image = ImageIO.read(getClass().getResource(imageFiles.get(i)));
                button.setIcon(new ImageIcon(image));
                button.setEnabled(true);
            } catch (Exception e) {

            }
        }

        ImageIcon blank = new ImageIcon(getClass().getResource(NONE));

        for (int j = imageFiles.size(); j < gameRows * gamePegSize; j++) {
            JButton button = (JButton) guessPanel.getComponent(j);
            try {
                button.setIcon(blank);
                button.setEnabled(true);
            } catch (Exception e) {
            }
        }
        validate();
    }

    void updateGameRows(int gameRows) {
        this.gameRows = gameRows;
        makeNewBoard();
    }

    void updatePegSize(int gamePegSize) {
        this.gamePegSize = gamePegSize;
        makeNewBoard();
    }
}