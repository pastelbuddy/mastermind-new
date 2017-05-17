package command;

import model.Feedback;
import model.GCReceiver;
import model.GameCommand;
import model.GameState;

/**
 * Created by Joanna Kanas
 */
public class SubmitFeedbackCommand implements GameCommand {

    private GCReceiver receiver;
    private Feedback feedback;
    private int gameRows;

    public SubmitFeedbackCommand(GCReceiver receiver, Feedback feedback, int gameRows) {
        this.receiver = receiver;
        this.feedback = feedback;
        this.gameRows = gameRows;
    }

    public void execute() {
        GameState newState = new GameState(receiver.getState());
        newState.addFeedback(feedback);
        newState.setStatus("Received CodeMaker's feedback. Waiting for Codebreaker's next guess.");

        if (newState.getFeedback().size() <= gameRows) {
            receiver.addState(newState);
            receiver.sendToLog("CodeMaker provided feedback: " + feedback);
        }

        WinCommand winCheck = new WinCommand(receiver);
        if (winCheck.win(gameRows)) {
            winCheck.execute();
        }
    }
}
