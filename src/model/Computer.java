package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Computer implements Player {

    private final int MAX_COLORS = CodePegs.values().length;
    private final String DIFFICULTY_LEVEL = "2";

    private final GameController controller;
    private Difficulty difficultyMode;

    private String difficulty;
    private final String name;
    @SuppressWarnings("unused")
    private Integer waitTime;

    public Computer(GameController controller) {
        this.controller = controller;
        this.name = this.getClass().getName();
        this.waitTime = 15;
        this.difficultyMode = new Novice();
    }

    public String getName() {
        return this.name;
    }

    public void setWaitTime(int seconds) {
        this.waitTime = seconds;
    }

    public void setDifficulty(String level) {
        this.difficulty = level;
    }

    public void makeCommand(GameCommand newCommand) {
        newCommand.execute();
    }

    public List<String> fillColor(int pegSize) {
        //Assign the CodePegs Values to this data structure
        difficulty = controller.getGameMode();
        boolean good = false;

        if (Objects.equals(DIFFICULTY_LEVEL, difficulty)) {
            difficultyMode = new Expert();
        } else {
            difficultyMode = new Novice();
        }

        List<String> newCode = new ArrayList<>();
        while (!good) {
            int choice;
            newCode.clear();
            CodePegs[] values = CodePegs.values();

            for (int i = 0; i < pegSize; i++) {
                choice = new Random().nextInt(MAX_COLORS);
                newCode.add(values[choice].toString());
            }
            Code c = new Code(newCode, 4);
            good = difficultyMode.isLegalCode(c);
        }
        return newCode;
    }

    public void submitCode(int pegSize) {
        controller.addGuess(fillColor(pegSize));
    }

}