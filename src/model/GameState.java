package model;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private Code answerCode;
    private final List<Code> guesses;
    private final List<Feedback> feedback;
    private String status;

    public GameState() {
        this.answerCode = null;
        this.guesses = new ArrayList<>();
        this.feedback = new ArrayList<>();
        this.status = "Press \"File\" > \"New Game\" to start a new game.";
}

    public GameState(GameState original) {
        if (original.answerCode != null) {
            CodePegs[] newCode = original.answerCode.getCode();
            answerCode = new Code(newCode, newCode.length);
        }
        guesses = new ArrayList<>(original.guesses);
        feedback = new ArrayList<>(original.feedback);
        status = original.status;
    }

    public Code getLastGuess() {
        return guesses.get(guesses.size() - 1);
    }

    public List<Code> getGuesses() {
        return this.guesses;
    }

    public List<Feedback> getFeedback() {
        return this.feedback;
    }

    public void addGuess(Code newGuess) {
        guesses.add(newGuess);
    }

    public void addFeedback(Feedback newFeedback) {
        feedback.add(newFeedback);
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAnswer(Code answer) {
        this.answerCode = answer;
    }

    public Code getAnswer() {
        return this.answerCode;
    }

    public int getNumTurns() {
        return guesses.size() + feedback.size();
    }

    public int getNumGamePlays() {
        int i = guesses.size() + feedback.size();
        if (answerCode != null) {
            i++;
        }
        return i;
    }

    public Feedback getLastFeedback() {
        if (feedback.size() == 0) {
            return null;
        }
        return feedback.get(feedback.size() - 1);
    }

    public List<String> createLogFromGameState() {
        List<String> newLog = new ArrayList<>();

        for (int i = 0; i < feedback.size(); i++) {
            newLog.add("Codebreaker guessed: " + guesses.get(i));
            newLog.add("CodeMaker provided feedback: " + feedback.get(i));
        }

        //checks to see if we are waiting for a feedback after a guess has been placed
        //if so we need to add the most recent guess to the log
        if (guesses.size() > feedback.size()) {
            newLog.add("Codebreaker guessed: " + guesses.get(guesses.size() - 1));
        }

        return newLog;
    }
}
