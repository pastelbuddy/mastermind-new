package model;

/**
 * Created by Joanna Kanas
 */
public class GUIObserver implements GameObserver {

    private GameState subject;
    private GameController gameController;
    private String next;

    GUIObserver(GameController gameController) {
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
