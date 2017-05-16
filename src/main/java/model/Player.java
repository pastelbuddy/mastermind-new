package model;

/**
 * Created by ad on 2017-05-14.
 */
public interface Player {

    void makeCommand(GameCommand newCommand);

    String getName();
}
