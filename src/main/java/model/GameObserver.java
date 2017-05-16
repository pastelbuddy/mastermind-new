package model;

/**
 * Created by ad on 2017-05-14.
 */
public interface GameObserver {

    /**
     * Gets the new state from the ConcreteSubject.
     */
    void update(GameSubject subject);
}
