package command;

import model.GCReceiver;
import model.GameCommand;

public class SubmitNewGameCommand implements GameCommand {

    private GCReceiver receiver;

    public SubmitNewGameCommand(GCReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.clearHistory();
    }

}
