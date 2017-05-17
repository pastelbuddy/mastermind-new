package command;

import model.Code;
import model.GCReceiver;
import model.GameCommand;
import model.GameState;

/**
 * Created by Joanna Kanas
 */
public class SubmitAnswerCommand implements GameCommand {

    private GCReceiver receiver;
    private Code answerCode;

    public SubmitAnswerCommand(GCReceiver receiver, Code answerCode) {
        this.receiver = receiver;
        this.answerCode = answerCode;
    }

    public void execute() {
        GameState newState = new GameState(receiver.getState());
        newState.setAnswer(answerCode);
        newState.setStatus("Codebreaker submitted code. Waiting for CodeBreaker's guess.");
        receiver.addState(newState);
        receiver.sendToLog("CodeMaker created answer code.");
    }

}