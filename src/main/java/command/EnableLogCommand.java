package command;

import model.GCReceiver;
import model.GameCommand;

/**
 * Created by Joanna Kanas
 */
public class EnableLogCommand implements GameCommand {

    private GCReceiver receiver;
    private String filename;

    public EnableLogCommand(GCReceiver receiver, String filename) {
        this.receiver = receiver;
        this.filename = filename;
    }

    @Override
    public void execute() {
        receiver.enableLog(filename);
    }
}
