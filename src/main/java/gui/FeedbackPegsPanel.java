package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Created by ad on 2017-05-14.
 */
public class FeedbackPegsPanel extends JPanel {

    private static final long serialVeriosnUID = 1L;
    private static final String NONE = "NONE.png";
    private JPanel pegsPanel;
    private int gameRows;
    private int gamePegSize;

    public FeedbackPegsPanel(Integer gameRows, Integer gamePegSize) {
        this.gameRows = gameRows;
        this.gamePegSize = gamePegSize;

        this.setLayout(new GridLayout(1, 1, 5, 5));
        this.setBorder(new EmptyBorder(10, 25, 50, 9));

        drawInitialPegs();
        this.add(pegsPanel);
    }

    private void drawInitialPegs() {
        // reset the guess panel and guesses
        pegsPanel = new JPanel();

        makeDefaultButtons();

        pegsPanel.setLayout(new GridLayout(gameRows, gamePegSize, 25, 7));
        pegsPanel.setBorder(new EmptyBorder(10, 25, 25, 25));
    }

    private void makeDefaultButtons() {
        ImageIcon blank = new ImageIcon(getClass().getResource(NONE));

        //make all "default" buttons
        for (int i = 0; i < gameRows; i++) {
            for (int j = 0; j < gamePegSize; j++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                button.setIcon(blank);
                pegsPanel.add(button);
            }
        }
    }

    void makeNewFeedbackBoard() {
        pegsPanel.removeAll();

        makeDefaultButtons();

        pegsPanel.setLayout(new GridLayout(gameRows, gamePegSize, 5, 7));
        pegsPanel.repaint();
    }

    void redrawFeedback(List<String> imageFiles) {
        for (int i = 0; i < imageFiles.size(); i++) {
            JButton button = (JButton) pegsPanel.getComponent(i);
            try {
                Image image = ImageIO.read(getClass().getResource(imageFiles.get(i)));
                button.setIcon(new ImageIcon(image));
                button.setEnabled(true);
            } catch (Exception e) {

            }
        }

        ImageIcon blank = new ImageIcon(getClass().getResource(NONE));

        for (int i = imageFiles.size(); i < gameRows * gamePegSize; i++) {
            JButton button = (JButton) pegsPanel.getComponent(i);
            try {
                button.setIcon(blank);
                button.setEnabled(true);
            } catch (Exception e) {

            }
        }
    }

    void updateGameRows(int gameRows) {
        this.gameRows = gameRows;
        makeNewFeedbackBoard();
    }

    void updatePegSize(int gamePegSize) {
        this.gamePegSize = gamePegSize;
        makeNewFeedbackBoard();
    }
}