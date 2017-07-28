package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class FeedbackPegsPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String NONE = "NONE.png";
    private JPanel pegsPanel;
    private int gameRows;
    private int gamePegSize;

    public FeedbackPegsPanel(Integer gameRows, Integer gamePegSize) {
        this.gameRows = gameRows;
        this.gamePegSize = gamePegSize;

        //Set the drawing properties for the super panel.
        this.setLayout(new GridLayout(1, 1, 5, 5));
        this.setBorder(new EmptyBorder(10, 25, 50, 9));

        // Make a sub-panel to display guesses.
        drawInitialPegs();
        this.add(pegsPanel);

    }

    private void drawInitialPegs() {
        // reset the guess panel and guesses.
        pegsPanel = new JPanel();

        makeDefaultButtons();

        // set the feedback panel's drawing properties.
        pegsPanel.setBorder(new EmptyBorder(10, 25, 25, 25));
        pegsPanel.setLayout(new GridLayout(this.gameRows, this.gamePegSize, 5, 7));
    }

    private void makeDefaultButtons() {
        ImageIcon blank = new ImageIcon(getClass().getResource(NONE));

        // make all "default" buttons.
        for (int i = 0; i < this.gameRows; i++) {
            for (int j = 0; j < this.gamePegSize; j++) {
                JButton button = new JButton();
                button.setBackground(Color.white);
                button.setIcon(blank);
                pegsPanel.add(button);
            }
        }
    }

    public void makeNewFeedbackBoard() {
        pegsPanel.removeAll();

        makeDefaultButtons();

        pegsPanel.setLayout(new GridLayout(this.gameRows, this.gamePegSize, 5, 7));
        pegsPanel.repaint();
    }

    public void redrawFeedback(List<String> imageFiles) {
        for (int i = 0; i < imageFiles.size(); i++) {
            JButton button = (JButton) pegsPanel.getComponent(i);
            try {
                Image img = ImageIO.read(getClass().getResource(imageFiles.get(i)));
                button.setIcon(new ImageIcon(img));
                button.setEnabled(true);
            } catch (Exception ignored) {
            }
        }

        ImageIcon blank = new ImageIcon(getClass().getResource(NONE));

        for (int j = imageFiles.size(); j < this.gameRows * this.gamePegSize; j++) {
            JButton button = (JButton) pegsPanel.getComponent(j);
            try {
                button.setIcon(blank);
                button.setEnabled(true);
            } catch (Exception ignored) {
            }
        }
    }

    public void setGameRows(int gameRows) {
        this.gameRows = gameRows;
        makeNewFeedbackBoard();
    }

    public void setPegSize(int gamePegSize) {
        this.gamePegSize = gamePegSize;
        makeNewFeedbackBoard();
    }

}
