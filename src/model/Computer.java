package model;

import java.util.ArrayList;
import java.util.Random;

public class Computer implements Player {

    private String difficulty;
    private GameController _controller;
    private Difficulty _difficultyMode;
    private String _name;
    @SuppressWarnings("unused")
    private Integer _waitTime;
    private boolean good = true;
    //Constants for Computer Logic (Making a guess)
    private final int MAX_COLORS = CodePegs.values().length;

    public Computer(GameController controller) {
        this._controller = controller;
        this._name = this.getClass().getName();
        this._waitTime = 15;
        this._difficultyMode = new Novice();
    }

    public String getName() {
        return this._name;
    }

    public void setWaitTime(int seconds) {
        this._waitTime = seconds;
    }

    public void setDifficulty(String level) {
        this.difficulty = level;
    }

    public void makeCommand(GameCommand newCommand) {
        newCommand.execute();
    }

    public ArrayList<String> fillColor(int pegSize) {
        //Assign the CodePegs Values to this data structure
        difficulty = _controller.getGameMode();
        good = false;
        if (difficulty.equals("2")) {
            _difficultyMode = new Expert();
        } else {
            _difficultyMode = new Novice();
        }
        ArrayList<String> newCode = new ArrayList<>();
        while (!good) {
            int choice;
            newCode.clear();
            CodePegs[] values = CodePegs.values();
            for (int i = 0; i < pegSize; i++) {
                choice = new Random().nextInt(MAX_COLORS);
                newCode.add(values[choice].toString());
            }
            Code c = new Code(newCode, 4);
            good = _difficultyMode.isLegalCode(c);
        }
        return newCode;
    }

    public void submitCode(int pegSize) {
        //try {Thread.sleep(_waitTime * 1000);}
        //catch (InterruptedException e) {}
        _controller.addGuess(fillColor(pegSize));
    }

}