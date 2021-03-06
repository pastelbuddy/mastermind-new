package command;

import model.Feedback;
import model.GCReceiver;
import model.GameCommand;
import model.GameState;

public class SubmitFeedbackCommand implements GameCommand {

    private final GCReceiver receiver;
    private final Feedback feedback;
    private final int gameRows;

    public SubmitFeedbackCommand(GCReceiver receiver, Feedback feedback, int gameRows) {
        this.receiver = receiver;
        this.feedback = feedback;
        this.gameRows = gameRows;
    }

    public void execute() {
        GameState newState = new GameState(receiver.getState());
        newState.addFeedback(feedback);
        newState.setStatus("Received CodeMaker's feedback. Waiting for Codebreaker's next guess.");

        // Prevent adding feedback beyond the gameRow limit.
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