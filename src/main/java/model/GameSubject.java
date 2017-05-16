package model;

/**
 * Created by ad on 2017-05-14.
 */
public interface GameSubject {

    /**
     * Adds an observer to the ConcreteSubject's collection of observers.
     */
    void attach(GameObserver gameObserver);

    /**
     * Removes an observer to the ConcreteSubject's collection of observers.
     */
    void detach(GameObserver gameObserver);

    /**
     * Notifies all observers of a change in the subject.
     */
    void notifyObservers();

    /**
     * Gets the most recent game state.
     *
     * @return The most recent game state.
     */
    GameState getState();

    /**
     * Adds a game state to the collection of game states.
     *
     * @param newState The game state to be added.
     */
    void addState(GameState newState);
}
