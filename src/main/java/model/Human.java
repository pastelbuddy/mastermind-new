package model;

import command.SubmitFeedbackCommand;
import command.SubmitGuessCommand;

import java.util.List;

/**
 * Created by Joanna Kanas
 */
public class Human implements Player {

    private GCReceiver rec;
    private String name;
    private GameController controller;

    Human(GameController controller, GCReceiver receiver) {

        this.rec = receiver;
        this.name = this.getClass().getName();
        this.controller = controller;
    }

    public String getName() {
        return this.name;
    }

    public void makeCommand(GameCommand newCommand) {
        newCommand.execute();
    }

    public void submitCode(List<CodePegs> code) {
        Code newCode = new Code(code, controller.getPegSize());
        GameCommand newGuess = new SubmitGuessCommand(rec, newCode, controller.getRows());
        makeCommand(newGuess);
    }

    public void submitFeedback(List<FeedbackPegs> feedback) {
        Feedback newFeedback = new Feedback(feedback, controller.getPegSize());
        GameCommand newFeedBack = new SubmitFeedbackCommand(rec, newFeedback, controller.getRows());
        makeCommand(newFeedBack);
    }
}
