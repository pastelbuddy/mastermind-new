package command;

import model.FeedbackPegs;
import model.GCReceiver;
import model.GameCommand;
import model.GameState;

public class WinCommand implements GameCommand {

    private GCReceiver receiver;
    private GameState currState;

    public WinCommand(GCReceiver receiver) {
        this.receiver = receiver;
        this.currState = this.receiver.getState();
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

        FeedbackPegs[] feedback = currState.getLastFeedback().getFeedback();
        for (FeedbackPegs aFeedback : feedback) {
            if (!aFeedback.equals(FeedbackPegs.BLACK)) {
                return false;
            }
        }
        return true;
    }

    private boolean codeMakerWin(int gameRows) {
        return currState.getNumTurns() == gameRows * 2;
    }

    public boolean win(int gameRows) {
        return codeBreakerWin() || codeMakerWin(gameRows);
    }

}
