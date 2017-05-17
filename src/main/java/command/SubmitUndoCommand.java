package command;

import model.GCReceiver;
import model.GameCommand;

/**
 * Created by Joanna Kanas
 */
public class SubmitUndoCommand implements GameCommand {

    private GCReceiver receiver;

    public SubmitUndoCommand(GCReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        //This little algorithm determines if we need to undo 1 or 2 states
        //and saves the result in numToUndo for readability
        int numToUndo = Math.abs((receiver.getState().getNumTurns() % 2) - 2);
        int minimumStates = receiver.getState().getNumGamePlays() + 1 - numToUndo;
        if (minimumStates >= 1) {
            receiver.undoStates(numToUndo);
            receiver.sendToLog("Undo command issued: Last state change undone.");
        }
    }

}
