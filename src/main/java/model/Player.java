package model;

/**
 * Created by Joanna Kanas
 */
public interface Player {

    void makeCommand(GameCommand newCommand);

    String getName();
}
