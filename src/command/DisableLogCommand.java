package command;

import model.GCReceiver;
import model.GameCommand;

public class DisableLogCommand implements GameCommand {

    private final GCReceiver receiver;

    public DisableLogCommand(GCReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.disableLog();
    }

}