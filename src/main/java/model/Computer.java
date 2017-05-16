package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Created by ad on 2017-05-14.
 */
public class Computer implements Player {

    //Constants for Computer Logic (Making a guess)
    private final int MAX_COLORS = CodePegs.values().length;

    private GameController controller;
    private Difficulty difficultyMode;
    private String name;

    public Computer(GameController controller) {
        this.controller = controller;
        this.name = this.getClass().getName();
        this.difficultyMode = new Novice();
    }

    public String getName() {
        return this.name;
    }

    /**
     * This method packages commands and sends them off
     * to the GameCommand class
     */
    public void makeCommand(GameCommand newCommand) {
        newCommand.execute();
    }

    /**
     * This method is made to make a code
     * with random CodePegs assigned to it
     *
     * @return newCode
     */
    public List<String> fillColor(int pegSize) {
        //Assign the CodePegs Values to this data structure
        String difficulty = controller.getGameMode();
        boolean good = false;
        if (Objects.equals("2", difficulty)) {
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
            Code c = new Code(newCode);
            good = difficultyMode.isLegalCode(c);
        }
        return newCode;
    }

    /**
     * This method will pass the code that the GUI makes
     * to the code class, and then make a GameCommand
     * to submit the new code
     *
     * @param pegSize an Array of CodePegs that make up a code
     */
    public void submitCode(int pegSize) {
        //try {Thread.sleep(waitTime * 1000);}
        //catch (InterruptedException e) {}
        controller.addGuess(fillColor(pegSize));
    }
}
