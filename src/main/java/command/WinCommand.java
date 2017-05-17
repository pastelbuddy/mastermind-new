package command;

import model.FeedbackPegs;
import model.GCReceiver;
import model.GameCommand;
import model.GameState;

import java.util.List;

/**
 * Created by Joanna Kanas
 */
public class WinCommand implements GameCommand {

    private GCReceiver receiver;
    private GameState currentState;

    public WinCommand(GCReceiver receiver) {
        this.receiver = receiver;
        this.currentState = this.receiver.getState();
    }

    public void execute() {
        //System.out.println("I won!");
        if (codeBreakerWin()) {
            receiver.updateStatus("Codebreaker has won!");
        } else {
            receiver.updateStatus("Codemaker has won!");
        }
    }

    private boolean codeBreakerWin() {
        List<FeedbackPegs> feedback = currentState.getLastFeedback().getFeedbackPegs();
        for (FeedbackPegs aFeedback : feedback) {
            if (!aFeedback.equals(FeedbackPegs.BLACK)) {
                return false;
            }
        }
        return true;
    }

    private boolean codeMakerWin(int gameRows) {
        return currentState.getNumTurns() == gameRows * 2;
    }

    public boolean win(int gameRows) {
        return codeBreakerWin() || codeMakerWin(gameRows);
    }
}
