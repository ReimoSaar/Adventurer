package game;

import java.awt.*;

public class FPS {
    private long lastFPSCheck = 0;
    private int currentFPS = 0;
    private int totalFrames = 0;

    public void renderFPS(Graphics g) {
         new Text(g, "game.FPS:" + currentFPS, 0.01f, 0.03f, 20, "arial", Color.BLACK, Font.BOLD);
        }

    public void update() {
        totalFrames++;
        if (System.nanoTime() > lastFPSCheck + 1000000000) {
            lastFPSCheck = System.nanoTime();
            currentFPS = totalFrames;
            totalFrames = 0;
        }
    }
}
