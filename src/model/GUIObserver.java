package model;

public class GUIObserver implements GameObserver {

    private GameState subject;
    private final GameController gameController;
    @SuppressWarnings("unused")
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
