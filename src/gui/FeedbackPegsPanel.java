package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class FeedbackPegsPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String NONE = "NONE.png";
    private JPanel _pegsPanel;
    private int _gameRows;
    private int _gamePegSize;

    /**
     * Makes a feed back panel with pegs
     * @return that panel
     */
    public FeedbackPegsPanel(Integer gameRows, Integer gamePegSize) {

        this._gameRows = gameRows;
        this._gamePegSize = gamePegSize;

        //Set the drawing properties for the super panel.
        this.setLayout(new GridLayout(1, 1, 5, 5));
        this.setBorder(new EmptyBorder(10, 25, 50, 9));

        // Make a sub-panel to display guesses.
        drawInitialPegs();
        this.add(_pegsPanel);

    }

    /**
     * The pegs for feedback are made in the same manner as board pegs
     * Each group is put into a separate group for editing.
     */
    public void drawInitialPegs() {

        // reset the guess panel and guesses.
        _pegsPanel = new JPanel();

        makeDefaultButtons();

        // set the feedback panel's drawing properties.
        _pegsPanel.setBorder(new EmptyBorder(10, 25, 25, 25));
        _pegsPanel.setLayout(new GridLayout(this._gameRows, this._gamePegSize, 5, 7));
    }

    private void makeDefaultButtons() {
        ImageIcon blank = new ImageIcon(getClass().getResource(NONE));

        // make all "default" buttons.
        for (int i = 0; i < this._gameRows; i++) {
            for (int j = 0; j < this._gamePegSize; j++) {
                JButton button = new JButton();
                button.setBackground(Color.white);
                button.setIcon(blank);
                _pegsPanel.add(button);
            }
        }
    }

    public void makeNewFeedbackBoard() {
        _pegsPanel.removeAll();

        makeDefaultButtons();

        _pegsPanel.setLayout(new GridLayout(this._gameRows, this._gamePegSize, 5, 7));
        _pegsPanel.repaint();
    }

    /**
     * Redraws the feedback given an array of image filenames.
     * @param imageFiles A list of filenames.
     */
    public void redrawFeedback(ArrayList<String> imageFiles) {
        for (int i = 0; i < imageFiles.size(); i++) {
            JButton button = (JButton) _pegsPanel.getComponent(i);
            try {
                Image img = ImageIO.read(getClass().getResource(imageFiles.get(i)));
                button.setIcon(new ImageIcon(img));
                button.setEnabled(true);
            } catch (Exception e) {
            }
        }

        ImageIcon blank = new ImageIcon(getClass().getResource(NONE));

        for (int j = imageFiles.size(); j < this._gameRows * this._gamePegSize; j++) {
            JButton button = (JButton) _pegsPanel.getComponent(j);
            try {
                button.setIcon(blank);
                button.setEnabled(true);
            } catch (Exception e) {
            }
        }
    }

    public void setGameRows(int gameRows) {
        this._gameRows = gameRows;
        makeNewFeedbackBoard();
    }

    public void setPegSize(int gamePegSize) {
        this._gamePegSize = gamePegSize;
        makeNewFeedbackBoard();
    }

}
