import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Background extends JPanel implements ActionListener {

    private Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    private double screenWidth = resolution.width;
    private int screenHeight = resolution.height;
    private static ArrayList<RectangleImage> level1Background;
    private static ArrayList<RectangleImage> level2Background;
    private static ArrayList<RectangleImage> level3Background;
    private static ArrayList<RectangleImage> level4Background;
    private static ArrayList<RectangleImage> level5Background;
    private static ArrayList<RectangleImage> level6Background;
    private static ArrayList<RectangleImage> level7Background;
    private static ArrayList<RectangleImage> level8Background;
    private static ArrayList<RectangleImage> level9Background;
    private Platform platform;
    private LevelStateManager levelState;

    public Background() {
        platform = new Platform();
        levelState = new LevelStateManager();
        level1Background = new ArrayList<>();
        level2Background = new ArrayList<>();
        level3Background = new ArrayList<>();
        level4Background = new ArrayList<>();
        level5Background = new ArrayList<>();
        level6Background = new ArrayList<>();
        level7Background = new ArrayList<>();
        level8Background = new ArrayList<>();
        level9Background = new ArrayList<>();
        for (int i = 0; i <= 1; i++) {
            level1Background.add(background("background").get(i));
        }
    }

    public ArrayList<RectangleImage> background(String string) {
        ArrayList<RectangleImage> backgounds = new ArrayList<>();
        backgounds.add(new RectangleImage(GameImage.getImage(string), 0.0f, 0.0f, 1.0f, 1.0f));
        backgounds.add(new RectangleImage(GameImage.getImage(string), 1.0f, 0.0f, 1.0f, 1.0f));
        return backgounds;
    }

    public void renderBackground(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (RectangleImage backgrounds : getAllBackgrounds()) {
            backgrounds.draw(g2, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (RectangleImage backgrounds : getAllBackgrounds()) {
            backgrounds.getRectangle().x += Math.round(platform.getxVel() / 2);
            
            if (backgrounds.getRectangle().x + backgrounds.getRectangle().width == 0) {
                backgrounds.getRectangle().x = (int) screenWidth;
            }
        }
    }

    public ArrayList<RectangleImage> getAllBackgrounds() {
        ArrayList<RectangleImage> backgounds = null;
        switch (levelState.getState()) {
            case LEVEL_1:
                backgounds = level1Background;
                break;
            case LEVEL_2:
                backgounds = level2Background;
                break;
            case LEVEL_3:
                backgounds = level3Background;
                break;
            case LEVEL_4:
                backgounds = level4Background;
                break;
            case LEVEL_5:
                backgounds = level5Background;
                break;
            case LEVEL_6:
                backgounds = level6Background;
                break;
            case LEVEL_7:
                backgounds = level7Background;
                break;
            case LEVEL_8:
                backgounds = level8Background;
                break;
            case LEVEL_9:
                backgounds = level9Background;
                break;
        }
        return backgounds;
    }

    public void prepareBackground() {
        for (RectangleImage background : level1Background) {
            background.getRectangle().x = (int) background.getX();
        }
        for (RectangleImage background : level2Background) {
            background.getRectangle().x = (int) background.getX();
        }
        for (RectangleImage background : level3Background) {
            background.getRectangle().x = (int) background.getX();
        }
        for (RectangleImage background : level4Background) {
            background.getRectangle().x = (int) background.getX();
        }
        for (RectangleImage background : level5Background) {
            background.getRectangle().x = (int) background.getX();
        }
        for (RectangleImage background : level6Background) {
            background.getRectangle().x = (int) background.getX();
        }
        for (RectangleImage background : level7Background) {
            background.getRectangle().x = (int) background.getX();
        }
        for (RectangleImage background : level8Background) {
            background.getRectangle().x = (int) background.getX();
        }
        for (RectangleImage background : level9Background) {
            background.getRectangle().x = (int) background.getX();
        }
    }

    public void saveBackground() {
        File outputFile;
        BufferedWriter outputWriter;

        try {
            outputFile = new File("save background.txt");
            outputWriter = new BufferedWriter(new FileWriter(outputFile));

            for (RectangleImage backgrounds : getAllBackgrounds()) {
                outputWriter.write(Double.toString(backgrounds.getRectangle().x / screenWidth) + "\n");
            }

            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBackground() {
        File inputFile;
        BufferedReader inputReader;

        try {
            inputFile = new File("save background.txt");
            inputReader = new BufferedReader(new FileReader(inputFile));

            for (RectangleImage backgrounds : getAllBackgrounds()) {
                backgrounds.getRectangle().x = (int) (Double.parseDouble(inputReader.readLine()) * screenWidth);
            }

            inputReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
