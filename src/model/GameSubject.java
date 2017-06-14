/**
 * GameSubject.java
 * <p>
 * GameSubject defines all methods necessary for all ConcreteSubjects,
 * which are Receivers for the Command pattern.
 *
 * @author peter
 */

package model;

public interface GameSubject {

    void attach(GameObserver gameObserver);

    void detach(GameObserver gameObserver);

    void notifyObservers();

    GameState getState();

    void addState(GameState newState);
}
