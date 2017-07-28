package model;

import command.*;
import gui.MainGUIPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameController {

    private static final Integer DEFAULT_GAME_ROWS = 10;
    private static final Integer DEFAULT_GAME_PEG_SIZE = 4;

    private static final String DEFAULT_GAME_MODE = "Novice";
    private static final String MODEL_COMPUTER = "model.Computer";
    private static final String MODEL_HUMAN = "model.Human";
    private static final String HUMAN = "human";
    private static final String WON = "won";
    private static final String WIN = "win";
    private static final String CP_FEEDBACK = "cp_feedbk";
    private static final String CP_GUESS = "cp_guess";

    private Player codeMaker;
    private Player codeBreaker;
    private Player SP;
    private GCReceiver receiver;
    private GameState currentState;
    private boolean newGame;
    private boolean win;

    private MainGUIPanel mainGUIPanel;

    private static String next;
    private static String gameMode;
    private int gameRows;
    private int gamePegSize;
    @SuppressWarnings("unused")
    private int count;

    public GameController() {
        codeMaker = new Human(this, receiver);
        codeBreaker = new Human(this, receiver);
        SP = new SystemPlayer();
        receiver = new GCReceiver(this);
        newGame = false;
        count = 0;
        next = "nope";

        mainGUIPanel = new MainGUIPanel(this, DEFAULT_GAME_ROWS, DEFAULT_GAME_PEG_SIZE);

        setGamePegSize(DEFAULT_GAME_PEG_SIZE);
        setGameRows(DEFAULT_GAME_ROWS);
        setGameMode(DEFAULT_GAME_MODE);
        receiver.addState(new GameState());
    }

    private Player getCurrentPlayer() {
        if (currentState.getNumGamePlays() % 2 == 0) {
            return codeMaker;
        } else {
            return codeBreaker;
        }
    }

    public String getGameMode() {
        return gameMode;
    }

    public int getPegSize() {
        return gamePegSize;
    }

    public int getRows() {
        return gameRows;
    }

    public void setConfiguration(List<String> settings) {
        for (int i = 0; i < settings.size(); i++) {
            if (i == 0) {
                if (Objects.equals("Human", settings.get(i))) {
                    codeBreaker = new Human(this, receiver);
                } else {
                    codeBreaker = new Computer(this);
                }
            } else if (i == 1 && newGame) {
                if (Objects.equals("Human", settings.get(i))) {
                    codeMaker = new Human(this, receiver);
                    mainGUIPanel.newHumanSolution();
                } else {
                    codeMaker = new Computer(this);
                    mainGUIPanel.newComputerSolution();
                    addAnswer(((Computer) codeMaker).fillColor(gamePegSize));
                }
            } else if (i == 2) {
                if (Objects.equals("true", settings.get(i))) {
                    new EnableLogCommand(receiver, settings.get(i + 1)).execute();
                } else {
                    new DisableLogCommand(receiver).execute();
                }
            } else if (i == 5) {
                if (Objects.equals(MODEL_COMPUTER, codeMaker.getName())) {
                    ((Computer) codeMaker).setWaitTime(Integer.parseInt(settings.get(i)));
                } else if (Objects.equals(MODEL_COMPUTER, codeBreaker.getName())) {
                    ((Computer) codeBreaker).setWaitTime(Integer.parseInt(settings.get(i)));
                }
            } else if (i == 6 && newGame) {
                setGameRows(Integer.parseInt(settings.get(i)));
            } else if (i == 7 && newGame) {
                setGameMode(settings.get(i));
                mainGUIPanel.drawBoard();
                newGame = false;
            }
        }
    }

    private void setGameMode(String gameMode) {
        GameController.gameMode = gameMode;
    }

    private void setGameRows(int gameRows) {
        this.gameRows = gameRows;
        if (mainGUIPanel != null) {
            mainGUIPanel.updateGameRows(gameRows);
        }
    }

    public void updateCodeBreaker(List<String> strings) {
        if (Objects.equals("h", strings.get(0))) {
            codeBreaker = new Human(this, receiver);
        } else {
            codeBreaker = new Computer(this);
        }
    }

    public void updateCodeMaker(String s) {
        if (Objects.equals("h", s)) {
            codeMaker = new Human(this, receiver);
        } else {
            codeMaker = new Computer(this);
        }
    }


    private void setGamePegSize(int gamePegSize) {
        this.gamePegSize = gamePegSize;
        if (mainGUIPanel != null) {
            mainGUIPanel.updateGameRows(gamePegSize);
        }
    }

    public void setGameState(GameState newState) {
        currentState = newState;
        List<String> done = new ArrayList<>();
        done.add("BLACK");
        done.add("BLACK");
        done.add("BLACK");
        done.add("BLACK");
        count += 1;
        if (currentState.getLastFeedback() != null) {
            if (currentState.getStatus().contains(WON) || Objects.equals(currentState.getLastFeedback().convertToArray(), done)) {
                win = true;
                next = WIN;
            }
        }
        GUIUpdate();
    }

    private void GUIUpdate() {
        mainGUIPanel.setStatus(currentState.getStatus());

        if (currentState.getAnswer() != null && !currentState.getStatus().contains(WON)) {
            if (Objects.equals(MODEL_COMPUTER, getCurrentPlayer().getName()) && codeMaker == getCurrentPlayer()) {
                next = CP_FEEDBACK;
            } else if (Objects.equals(MODEL_COMPUTER, getCurrentPlayer().getName()) && codeBreaker == getCurrentPlayer()) {
                next = CP_GUESS;
            }

            if (Objects.equals(MODEL_HUMAN, getCurrentPlayer().getName()) && codeMaker == getCurrentPlayer()) {
                mainGUIPanel.showFeedbackPanel();
                next = HUMAN;
            } else if (Objects.equals(MODEL_HUMAN, getCurrentPlayer().getName()) && codeBreaker == getCurrentPlayer()) {
                mainGUIPanel.showGuessPanel();
                next = HUMAN;
            }
        }
    }

    public void drawBoard() {
        try {
            mainGUIPanel.redraw();
        } catch (NullPointerException ignored) {
        }
    }

    private void whosNext() {
        if (Objects.equals(CP_GUESS, next) && Objects.equals(MODEL_COMPUTER, getCurrentPlayer().getName()) && !win) {
            drawBoard();
            computerGuess();
        } else if (Objects.equals(CP_FEEDBACK, next) && Objects.equals(MODEL_COMPUTER, getCurrentPlayer().getName()) && !win) {
            drawBoard();
            computerFeedback();
        }
    }

    private void computerFeedback() {
        Runnable temp = () -> {
            Feedback f = ((SystemPlayer) SP).generateFeedback(currentState.getLastGuess(), currentState.getAnswer());
            addFeedback(f.convertToArray());
        };
        SwingUtilities.invokeLater(temp);
    }

    private void computerGuess() {
        Runnable temp = () -> ((Computer) getCurrentPlayer()).submitCode(gamePegSize);
        SwingUtilities.invokeLater(temp);
    }

    public List<String> generateComputerGuess() {
        return ((Computer) getCurrentPlayer()).fillColor(gamePegSize);
    }

    public void addGuess(List<String> pegNames) {
        Code guess = new Code(pegNames, pegNames.size());
        GameCommand newGuess = new SubmitGuessCommand(receiver, guess, gameRows);
        getCurrentPlayer().makeCommand(newGuess);

        isGUI();
    }

    private void isGUI() {
        mainGUIPanel.validate();
        drawBoard();
        whosNext();
        drawBoard();
        mainGUIPanel.validate();
    }

    public void addFeedback(List<String> pegNames) {
        Feedback feedback = new Feedback(pegNames, gamePegSize);
        GameCommand newFeedback = new SubmitFeedbackCommand(receiver, feedback, gameRows);
        getCurrentPlayer().makeCommand(newFeedback);

        isGUI();
    }

    public void addAnswer(List<String> pegNames) {
        Code code = new Code(pegNames, gamePegSize);
        GameCommand newGuess = new SubmitAnswerCommand(receiver, code);
        getCurrentPlayer().makeCommand(newGuess);

        isGUI();
    }

    public List<String> makeGuessImageNameList() {
        return Code.makeGuessImageList(currentState.getGuesses());
    }

    public List<String> makeFeedbackImageNameList() {
        return Feedback.makeFeedbackImageNameList(currentState.getFeedback());
    }

    public List<String> makeSolutionImageNameList() {
        List<Code> answerString = new ArrayList<>();
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