package command;

import model.Code;
import model.GCReceiver;
import model.GameCommand;
import model.GameState;

public class SubmitGuessCommand implements GameCommand {

    private GCReceiver receiver;
    private Code guess;
    private int gameRows;

    public SubmitGuessCommand(GCReceiver receiver, Code guess, int gameRows) {
        this.receiver = receiver;
        this.guess = guess;
        this.gameRows = gameRows;
    }

    public void execute() {
        GameState newState = new GameState(receiver.getState());
        newState.setStatus("Codebreaker provided guess. Waiting for feedback...");
        newState.addGuess(guess);

        // Prevent adding guesses beyond the gameRow limit.
        if (newState.getGuesses().size() > gameRows) {
            return;
        }

        receiver.addState(newState);
        receiver.sendToLog("Codebreaker guessed: " + guess);
    }

}
