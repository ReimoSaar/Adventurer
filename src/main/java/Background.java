import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.*;
import java.util.ArrayList;

public class Background {

    private Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    private double screenWidth = resolution.width;
    private int screenHeight = resolution.height;
    private ArrayList<RectangleImage> level1Background;
    private ArrayList<RectangleImage> level2Background;
    private ArrayList<RectangleImage> level3Background;
    private ArrayList<RectangleImage> level4Background;
    private ArrayList<RectangleImage> level5Background;
    private ArrayList<RectangleImage> level6Background;
    private ArrayList<RectangleImage> level7Background;
    private ArrayList<RectangleImage> level8Background;
    private ArrayList<RectangleImage> level9Background;

    public Background() {
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

    public void renderBackground(Graphics g, ActionListener imageObserver, LevelStateManager levelState) {
        Graphics2D g2 = (Graphics2D) g;
        for (RectangleImage backgrounds : getAllBackgrounds(levelState)) {
            backgrounds.draw(g2, imageObserver);
        }
    }

    public void update(Game game) {
        for (RectangleImage backgrounds : getAllBackgrounds(game.getLevelState())) {
            backgrounds.getRectangle().x += Math.round(game.getPlatform().getxVel() / 2);
            
            if (backgrounds.getRectangle().x + backgrounds.getRectangle().width == 0) {
                backgrounds.getRectangle().x = (int) screenWidth;
            }
        }
    }

    public ArrayList<RectangleImage> getAllBackgrounds(LevelStateManager levelState) {
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

    public void saveBackground(LevelStateManager levelState) {
        File outputFile;
        BufferedWriter outputWriter;

        try {
            outputFile = new File("save background.txt");
            outputWriter = new BufferedWriter(new FileWriter(outputFile));

            for (RectangleImage backgrounds : getAllBackgrounds(levelState)) {
                outputWriter.write(Double.toString(backgrounds.getRectangle().x / screenWidth) + "\n");
            }

            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBackground(LevelStateManager levelState) {
        File inputFile;
        BufferedReader inputReader;

        try {
            inputFile = new File("save background.txt");
            inputReader = new BufferedReader(new FileReader(inputFile));

            for (RectangleImage backgrounds : getAllBackgrounds(levelState)) {
                backgrounds.getRectangle().x = (int) (Double.parseDouble(inputReader.readLine()) * screenWidth);
            }

            inputReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
