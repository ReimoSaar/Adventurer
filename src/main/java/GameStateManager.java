public class GameStateManager {

    private GameState gameState = GameState.MENU;

    public GameState getState() {
        return gameState;
    }

    public void setState(GameState newState) {
        gameState = newState;
    }
}
