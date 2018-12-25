import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FPS implements ActionListener {
    private long lastFPSCheck = 0;
    private int currentFPS = 0;
    private int totalFrames = 0;

    public void renderFPS(Graphics g) {
         new Text(g, "FPS:" + currentFPS, 0.01f, 0.03f, 20, "arial", Color.BLACK, Font.BOLD);
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        totalFrames++;
        if (System.nanoTime() > lastFPSCheck + 1000000000) {
            lastFPSCheck = System.nanoTime();
            currentFPS = totalFrames;
            totalFrames = 0;
        }
    }
}
