package model;

public interface GameSubject {

    void attach(GameObserver gameObserver);

    void detach(GameObserver gameObserver);

    void notifyObservers();

    GameState getState();

    void addState(GameState newState);
}
