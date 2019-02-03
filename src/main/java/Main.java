import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Game game = new Game();
        Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
        obj.setSize(screenResolution.width, screenResolution.height);
        obj.setExtendedState(JFrame.MAXIMIZED_BOTH);
        obj.setUndecorated(true);
        obj.setVisible(true);
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(game);
        game.run();
    }
}
