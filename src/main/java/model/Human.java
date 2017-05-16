package model;

/**
 * Created by ad on 2017-05-14.
 */
public class Human implements Player {

    private GCReceiver rec;
    private String name;
    private GameController controller;

    public Human(GameController controller, GCReceiver receiver) {

        this.rec = receiver;
        this.name = this.getClass().getName();
        this.controller = controller;
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
     * This method will pass the code that the GUI makes
     * to the code class, and then make a GameCommand
     * to submit the new code
     *
     * @param code an Array of CodePegs which consist of user chosen colors for a given guess code
     */
    public void submitCode(CodePegs[] code) {
        Code newCode = new Code(code, controller.getPegSize());
        GameCommand newGuess = new SubmitGuessCommand(rec, newCode, controller.getRows());
        makeCommand(newGuess);
    }

    /**
     * This method will pass the feedback that the GUI makes
     * to the Feedback class, and then make a GameCommand
     * to submit the new feedback
     *
     * @param feedback an Array of FeedBack Pegs which consist of 0-4 black pegs or white pegs
     */
    public void submitFeedback(FeedbackPegs[] feedback) {
        Feedback newFeedback = new Feedback(feedback, controller.getPegSize());
        GameCommand newFeedBack = new SubmitFeedbackCommand(rec, newFeedback, controller.getRows());
        makeCommand(newFeedBack);
    }
}
