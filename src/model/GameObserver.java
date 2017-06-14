/**
 * GameObserver.java
 * <p>
 * GameObserver defines all methods necessary for all ConcreteObservers,
 * which are GUI Components in the system.
 *
 * @author: peter
 */

package model;

public interface GameObserver {

    void update(GameSubject subject);
}
