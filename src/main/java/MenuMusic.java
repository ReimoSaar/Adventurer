import java.io.File;

public class MenuMusic {
    AudioPlayer music1;
    private GameStateManager gameState;

    public MenuMusic() {
        music1 = new AudioPlayer("music/Off Limits.wav");
        gameState = new GameStateManager();
    }

    public void playMenuMusic() {
        if (gameState.getState() == GameState.MENU) {
            music1.play();
        } else {
            music1.stop();
        }
    }
}
