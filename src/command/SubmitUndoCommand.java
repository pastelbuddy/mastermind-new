package command;

import model.GCReceiver;
import model.GameCommand;

public class SubmitUndoCommand implements GameCommand {

    private final GCReceiver receiver;

    public SubmitUndoCommand(GCReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        int numToUndo = Math.abs((receiver.getState().getNumTurns() % 2) - 2);
        int minimumStates = receiver.getState().getNumGamePlays() + 1 - numToUndo;

        if (minimumStates >= 1) {
            receiver.undoStates(numToUndo);
        }
    }

}
