/**
 * Player.java
 * <p>
 * This is the Player interface which Humans and Computers will implement
 * from to create a GameCommand that will be passed to the Controller
 *
 * @author Josh Eklund
 */

package model;

public interface Player {

    void makeCommand(GameCommand newCommand);

    String getName();

}
