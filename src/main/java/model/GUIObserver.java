package model;

/**
 * Created by ad on 2017-05-14.
 */
public class GUIObserver {

    private GameState subject;
    private GameController gameController;
    private String next;

    public GUIObserver(GameController gameController) {
        this.gameController = gameController;
    }

    public void update(GameSubject subject) {
        this.subject = subject.getState();
        gameController.setGameState(this.subject);
        this.gameController.drawBoard();
    }

    public GameState getState() {
        return this.subject;
    }
}
