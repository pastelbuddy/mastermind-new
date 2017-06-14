package model;

import command.*;
import gui.MainGUIPanel;

import javax.swing.*;
import java.util.ArrayList;

public class GameController {

    /* Required instance variables for all games */
    private Player CM, CB, SP;
    private GCReceiver receiver;
    private GameState currentState;
    private boolean newGame;
    private boolean isGUI;
    private boolean win;

    /* Game user interfaces */
    private MainGUIPanel _GUI;

    /* Dynamically defined primitives set at start of game  */
    private static final Integer DEFAULT_GAME_ROWS = 10;
    private static final Integer DEFAULT_GAME_PEG_SIZE = 4;
    private static final String DEFAULT_GAME_MODE = "Novice";
    private static String next;
    private String gameMode;
    private int gameRows;
    private int gamePegSize;
    @SuppressWarnings("unused")
    private int count;

    public GameController(String userInterface) {
        CM = new Human(this, receiver);
        CB = new Human(this, receiver);
        SP = new SystemPlayer();
        receiver = new GCReceiver(this);
        newGame = false;
        count = 0;
        next = "nope";
        if (userInterface.equals("GUI")) {
            _GUI = new MainGUIPanel(this, DEFAULT_GAME_ROWS, DEFAULT_GAME_PEG_SIZE);
            isGUI = true;
        }
        setGamePegSize(DEFAULT_GAME_PEG_SIZE);
        setGameRows(DEFAULT_GAME_ROWS);
        setGameMode(DEFAULT_GAME_MODE);
        if (isGUI) {
            receiver.addState(new GameState());
        }
    }

    // Getters

    private Player getCurrentPlayer() {
        if (currentState.getNumGamePlays() % 2 == 0) {
            return CM;
        } else {
            return CB;
        }
    }

    public String getGameMode() {
        return this.gameMode;
    }

    public int getPegSize() {
        return this.gamePegSize;
    }

    public int getRows() {
        return this.gameRows;
    }

    // Setters

    public void setConfiguration(ArrayList<String> settings) {
        for (int i = 0; i < settings.size(); i++) {
            if (i == 0) {
                if (settings.get(i).equals("Human")) {
                    CB = new Human(this, receiver);
                } else {
                    CB = new Computer(this);
                }
            } else if (i == 1 && newGame) {
                if (settings.get(i).equals("Human")) {
                    CM = new Human(this, receiver);
                    _GUI.newHumanSolution();
                } else {
                    CM = new Computer(this);
                    _GUI.newComputerSolution();
                    addAnswer(((Computer) CM).fillColor(gamePegSize));
                }
            } else if (i == 2) {
                if (settings.get(i).equals("true")) {
                    new EnableLogCommand(receiver, settings.get(i + 1)).execute();
                } else {
                    new DisableLogCommand(receiver).execute();
                }
            } else if (i == 5) {
                if (CM.getName().equals("model.Computer")) {
                    ((Computer) CM).setWaitTime(Integer.parseInt(settings.get(i)));
                } else if (CB.getName().equals("model.Computer")) {
                    ((Computer) CB).setWaitTime(Integer.parseInt(settings.get(i)));
                }
            } else if (i == 6 && newGame) {
                setGameRows(Integer.parseInt(settings.get(i)));
            } else if (i == 7 && newGame) {
                setGameMode(settings.get(i));
                _GUI.drawBoard();
                newGame = false;
            }
        }
    }


    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public void setGameRows(int gameRows) {
        this.gameRows = gameRows;
        if (_GUI != null) {
            _GUI.updateGameRows(gameRows);
        }
    }

    /**
     * CLI updateCodeBreaker
     */
    public void updateCodeBreaker(ArrayList<String> strings) {
        if (strings.get(0).equals("h")) {
            CB = new Human(this, receiver);
        } else {
            CB = new Computer(this);
        }
    }

    public void updateCodeMaker(String s) {
        if (s.equals("h")) {
            CM = new Human(this, receiver);
        } else {
            CM = new Computer(this);
        }
    }


    public void setGamePegSize(int gamePegSize) {
        this.gamePegSize = gamePegSize;
        if (_GUI != null) {
            _GUI.updateGameRows(gamePegSize);
        }
    }

    public void setNewGame(boolean b) {
        newGame = false;
    }

    public void setGameState(GameState newState) {
        currentState = newState;
        ArrayList<String> done = new ArrayList<>();
        done.add("BLACK");
        done.add("BLACK");
        done.add("BLACK");
        done.add("BLACK");
        count += 1;
        if (currentState.getLastFeedback() != null) {
            if (currentState.getStatus().contains("won") || currentState.getLastFeedback().convertToArray().equals(done)) {
                win = true;
                next = "win";
            }
        }
        if (isGUI) {
            GUIUpdate();
        }
    }

    public void GUIUpdate() {
        _GUI.setStatus(currentState.getStatus());
        if (currentState.getAnswer() != null && !currentState.getStatus().contains("won")) {
            if (getCurrentPlayer().getName().equals("model.Computer") && CM == getCurrentPlayer()) {
                next = "cp_feedbk";
            } else if (getCurrentPlayer().getName().equals("model.Computer") && CB == getCurrentPlayer()) {
                next = "cp_guess";
            }
            if (getCurrentPlayer().getName().equals("model.Human") && CM == getCurrentPlayer()) {
                _GUI.showFeedbackPanel();
                next = "human";
            } else if (getCurrentPlayer().getName().equals("model.Human") && CB == getCurrentPlayer()) {
                _GUI.showGuessPanel();
                next = "human";
            }
        }
    }

    public void drawBoard() {
        try {
            _GUI.redraw();
        } catch (NullPointerException e) {
        }
    }

    public void whosNext() {
        if (next.equals("cp_guess") && getCurrentPlayer().getName().equals("model.Computer") && !win) {
            drawBoard();
            computerGuess();
        } else if (next.equals("cp_feedbk") && getCurrentPlayer().getName().equals("model.Computer") && !win) {
            drawBoard();
            computerFeedback();
        }
    }

    public void computerFeedback() {
        if (isGUI) {
            Runnable temp = () -> {
                Feedback f = ((SystemPlayer) SP).generateFeedback(currentState.getLastGuess(), currentState.getAnswer());
                addFeedback(f.convertToArray());
            };
            SwingUtilities.invokeLater(temp);

        } else {
            Feedback f = ((SystemPlayer) SP).generateFeedback(currentState.getLastGuess(), currentState.getAnswer());
            addFeedback(f.convertToArray());
        }
    }

    public void computerGuess() {
        Runnable temp = () -> ((Computer) getCurrentPlayer()).submitCode(gamePegSize);
        SwingUtilities.invokeLater(temp);
    }

    public ArrayList<String> generateComputerGuess() {
        return ((Computer) getCurrentPlayer()).fillColor(gamePegSize);
    }

    public void addGuess(ArrayList<String> pegNames) {
        Code guess = new Code(pegNames, pegNames.size());
        GameCommand newGuess = new SubmitGuessCommand(receiver, guess, gameRows);
        getCurrentPlayer().makeCommand(newGuess);

        isGUI();
    }

    private void isGUI() {
        if (isGUI) {
            _GUI.validate();
            drawBoard();
            whosNext();
            drawBoard();
            _GUI.validate();
        }
    }

    public void addFeedback(ArrayList<String> pegNames) {
        Feedback feedback = new Feedback(pegNames, gamePegSize);
        GameCommand newFeedback = new SubmitFeedbackCommand(receiver, feedback, gameRows);
        getCurrentPlayer().makeCommand(newFeedback);

        isGUI();
    }

    public void addAnswer(ArrayList<String> pegNames) {
        Code code = new Code(pegNames, gamePegSize);
        GameCommand newGuess = new SubmitAnswerCommand(receiver, code);
        getCurrentPlayer().makeCommand(newGuess);
        isGUI();
    }

    public ArrayList<String> makeGuessImageNameList() {
        return Code.makeGuessImageList(currentState.getGuesses());
    }

    public ArrayList<String> makeFeedbackImageNameList() {
        return Feedback.makeFeedbackImageNameList(currentState.getFeedback());
    }

    public ArrayList<String> makeSolutionImageNameList() {
        ArrayList<Code> answerString = new ArrayList<>();
        answerString.add(currentState.getAnswer());
        return Code.makeGuessImageList(answerString);
    }

    public void startNewGame() {
        newGame = true;
        win = false;
        getCurrentPlayer().makeCommand(new SubmitNewGameCommand(receiver));
    }

    public void undo() {
        new SubmitUndoCommand(this.receiver).execute();
    }

    public void restartGame() {
        while (currentState.getGuesses().size() > 0) {
            undo();
        }
    }
}