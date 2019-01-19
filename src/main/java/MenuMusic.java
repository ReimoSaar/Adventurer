import java.io.File;

public class MenuMusic {
    AudioPlayer music1;

    public MenuMusic() {
        music1 = new AudioPlayer("music/Off Limits.wav");
    }

    public void playMenuMusic(GameStateManager gameState) {
        if (gameState.getState() == GameState.MENU) {
            music1.play();
        } else {
            music1.stop();
        }
    }
}
