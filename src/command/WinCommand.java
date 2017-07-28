package command;

import model.FeedbackPegs;
import model.GCReceiver;
import model.GameCommand;
import model.GameState;

import java.util.Objects;

public class WinCommand implements GameCommand {

    private final GCReceiver receiver;
    private final GameState currState;

    public WinCommand(GCReceiver receiver) {
        this.receiver = receiver;
        this.currState = this.receiver.getState();
    }

    public void execute() {
        if (codeBreakerWin()) {
            receiver.updateStatus("Codebreaker has won!");
        } else {
            receiver.updateStatus("Codemaker has won!");
        }
    }

    private boolean codeBreakerWin() {
        FeedbackPegs[] feedback = currState.getLastFeedback().getFeedback();

        for (FeedbackPegs aFeedback : feedback) {
            if (!Objects.equals(FeedbackPegs.BLACK, aFeedback)) {
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
