/**
 * Human.java
 * <p>
 * This is a Human Player that overrides both the Player functions.
 *
 * @author Josh Eklund
 */

package model;

import command.SubmitFeedbackCommand;
import command.SubmitGuessCommand;

public class Human implements Player {

    private GCReceiver receiver;
    private String name;
    private GameController controller;

    public Human(GameController controller, GCReceiver receiver) {

        this.receiver = receiver;
        this.name = this.getClass().getName();
        this.controller = controller;
    }

    public String getName() {
        return this.name;
    }

    public void makeCommand(GameCommand newCommand) {
        newCommand.execute();
    }

    public void submitCode(CodePegs[] code) {
        Code newCode = new Code(code, controller.getPegSize());
        GameCommand newGuess = new SubmitGuessCommand(receiver, newCode, controller.getRows());
        makeCommand(newGuess);
    }

    public void submitFeedBack(FeedbackPegs[] feedback) {
        Feedback newFeedback = new Feedback(feedback, controller.getPegSize());
        GameCommand newFeedBack = new SubmitFeedbackCommand(receiver, newFeedback, controller.getRows());
        makeCommand(newFeedBack);
    }
}