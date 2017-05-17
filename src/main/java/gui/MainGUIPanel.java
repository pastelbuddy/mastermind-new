package gui;

import model.GameController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Joanna Kanas
 */
public class MainGUIPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel cards;
    private BoardPanel boardPanel;
    private FeedbackPegsPanel feedbackPegsPanel;
    private GameController gameController;
    private JLabel gameStatus;
    private SolutionPanel solutionPanel;

    public MainGUIPanel(GameController gameController, Integer gameRows, Integer gamePegSize) {

        solutionPanel = new SolutionPanel(this);

        gameStatus = new JLabel("Game Status: ");

        this.gameController = gameController;
        FileMenu fileMenu = new FileMenu();
        SubmitFeedbackPanel feedbackPanel = new SubmitFeedbackPanel();
        SubmitGuessPanel guessPanel = new SubmitGuessPanel();
        //this.gameRows = gameRows;
        //this.gamePegSize = gamePegSize;
        boardPanel = new BoardPanel(gameRows, gamePegSize);
        feedbackPegsPanel = new FeedbackPegsPanel(gameRows, gamePegSize);

        cards = new JPanel(new CardLayout());

        ((CardLayout) cards.getLayout()).addLayoutComponent(guessPanel.makeGuessPanel(this), "guess");
        ((CardLayout) cards.getLayout()).addLayoutComponent(feedbackPanel.makeFeedPanel(this), "feedback");
        cards.add(guessPanel.makeGuessPanel(this), "guess");
        cards.add(feedbackPanel.makeFeedPanel(this), "feedback");

        setLayout(new BorderLayout());

        add(gameStatus, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(feedbackPegsPanel, BorderLayout.EAST);
        add(cards, BorderLayout.SOUTH);

        setJMenuBar(fileMenu.makeFileMenu(this));

        setSize(650, 800);
        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        // Move the window
        setLocation(x, y);
        setVisible(true);
        setResizable(true);
        setTitle("Mastermind");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    SolutionPanel getSolutionPanel() {
        return solutionPanel;
    }

    public void showFeedbackPanel() {
        ((CardLayout) cards.getLayout()).show(cards, "feedback");
    }

    public void showGuessPanel() {
        ((CardLayout) cards.getLayout()).show(cards, "guess");
    }

    void newGame() {
        gameController.startNewGame();
    }

    public void redraw() {
        boardPanel.redrawGuesses(gameController.makeGuessImageNameList());
        feedbackPegsPanel.redrawFeedback(gameController.makeFeedbackImageNameList());
        solutionPanel.redrawSolution(gameController.makeSolutionImageNameList());
        validate();
    }

    public void drawBoard() {
        boardPanel.makeNewBoard();
        feedbackPegsPanel.makeNewFeedbackBoard();
        boardPanel.repaint();
        validate();
    }

    void addGuess(List<String> pegNames) {
        gameController.addGuess(pegNames);
    }

    void addFeedback(List<String> pegNames) {
        gameController.addFeedback(pegNames);
    }

    public void newHumanSolution() {
        solutionPanel.newGame();
    }

    public void newComputerSolution() {
        solutionPanel.newGameComputer();
    }

    void setOptions(List<String> settings) {
        gameController.setConfiguration(settings);
    }

    void setSolutionCode(List<String> str) {
        gameController.addAnswer(str);
    }

    public void setStatus(String s) {
        if (s != null) {
            gameStatus.setText("Game Status: " + s);
        }
    }

    void undo() {
        gameController.undo();
    }

    public void updateGameRows(int gameRows) {
        boardPanel.updateGameRows(gameRows);
        feedbackPegsPanel.updateGameRows(gameRows);
    }

    public void updatePegSize(int pegSize) {
        boardPanel.updatePegSize(pegSize);
        feedbackPegsPanel.updatePegSize(pegSize);
    }
}
