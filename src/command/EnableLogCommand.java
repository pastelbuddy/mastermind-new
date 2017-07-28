package command;

import model.GCReceiver;
import model.GameCommand;

public class EnableLogCommand implements GameCommand {

    private final GCReceiver receiver;
    private final String filename;

    public EnableLogCommand(GCReceiver receiver, String filename) {
        this.receiver = receiver;
        this.filename = filename;
    }

    @Override
    public void execute() {
        receiver.enableLog(filename);
    }
}
