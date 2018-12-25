public class GameStateManager {

    private static GameState gameState = GameState.MENU;

    public GameState getState() {
        return gameState;
    }

    public void setState(GameState newState) {
        gameState = newState;
    }

    public boolean isInMenu() {
        if (getState() == GameState.MENU) {
            return true;
        }
        return false;
    }
}
