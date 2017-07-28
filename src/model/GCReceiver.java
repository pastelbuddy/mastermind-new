package model;

import java.util.ArrayList;
import java.util.List;

public class GCReceiver implements GameSubject {

    private final List<GameObserver> observers;
    private final List<GameState> pastStates;
    private final Log log;

    public GCReceiver(GameController gameController) {
        observers = new ArrayList<>();
        observers.add(new GUIObserver(gameController));
        pastStates = new ArrayList<>();
        log = new Log();
    }

    public List<GameState> getPastStates() {
        return pastStates;
    }

    public void attach(GameObserver gameObserver) {
        this.observers.add(gameObserver);
    }

    public void detach(GameObserver gameObserver) {
        this.observers.remove(gameObserver);
    }

    public void notifyObservers() {
        for (GameObserver observer : this.observers) {
            observer.update(this);
        }
    }

    public GameState getState() {
        return this.pastStates.get(this.pastStates.size() - 1);
    }

    public void addState(GameState newState) {
        this.pastStates.add(newState);
        notifyObservers();
    }

    public void undoStates(int numStatesUndo) {
        for (int i = numStatesUndo; i > 0; i--) {
            this.pastStates.remove(pastStates.size() - 1);
        }

        GameState tempState = new GameState(getState());
        this.pastStates.remove(this.pastStates.size() - 1);

        this.addState(tempState);
    }

    public void sendToLog(String event) {
        if (log.getEnabled()) {
            log.addEvent(event);
        }
    }

    public void enableLog(String filename) {
        if (log.getEnabled()) {
            log.setEnabled(false);
        }
        log.setFilename(filename);
        log.setEnabled(true);

        for (String event : getState().createLogFromGameState()) {
            log.addEvent(event);
        }
    }

    public void updateStatus(String newStatus) {
        GameState recentState = getState();
        recentState.setStatus(newStatus);
        notifyObservers();
    }

    public void disableLog() {
        log.setEnabled(false);
    }

    public void clearHistory() {
        GameState tempState = new GameState(pastStates.get(0));
        pastStates.clear();
        pastStates.add(tempState);
    }
}
