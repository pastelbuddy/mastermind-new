package command;

import model.GCReceiver;
import model.GameCommand;

/**
 * Created by Joanna Kanas
 */
public class DisableLogCommand implements GameCommand {

    private GCReceiver receiver;

    public DisableLogCommand(GCReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.disableLog();
    }

}
