import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Platform {
    private ArrayList<RectangleImage> level1Platforms;
    private ArrayList<RectangleImage> level2Platforms;
    private ArrayList<RectangleImage> level3Platforms;
    private ArrayList<RectangleImage> level4Platforms;
    private ArrayList<RectangleImage> level5Platforms;
    private ArrayList<RectangleImage> level6Platforms;
    private ArrayList<RectangleImage> level7Platforms;
    private ArrayList<RectangleImage> level8Platforms;
    private ArrayList<RectangleImage> level9Platforms;
    private float platformxVel;
    private Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    private double screenWidth = resolution.width;

    public Platform() {
        //level 1
        level1Platforms = new ArrayList<>();
        level1Platforms.add(new RectangleImage(GameImage.getImage("platform"), 0.4f, 0.8f, 0.1f, 0.04f));
        level1Platforms.add(new RectangleImage(GameImage.getImage("platform"), 0.5f, 0.75f, 0.1f, 0.04f));
        level1Platforms.add(new RectangleImage(GameImage.getImage("platform"), 0.3f, 0.75f, 0.1f, 0.04f));
        level1Platforms.add(new RectangleImage(GameImage.getImage("platform"), 0.5f, 0.37f, 0.1f, 0.04f));
        level1Platforms.add(new RectangleImage(GameImage.getImage("platform"), 0.6f, 0.6f, 3.0f, 0.06f));
        level1Platforms.add(new RectangleImage(GameImage.getImage("platform"), 2.4f, 0.8f, 0.1f, 0.04f));
        level1Platforms.add(new RectangleImage(GameImage.getImage("platform"), 1.6f, 0.75f, 0.1f, 0.04f));
        level1Platforms.add(new RectangleImage(GameImage.getImage("platform"), 3.0f, 0.5f, 3.0f, 0.04f));
        level1Platforms.add(new RectangleImage(GameImage.getImage("platform"), 0.8f, 0.2f, 0.2f, 0.8f));
        //level1Platforms.add(new RectangleImage(GameImage.getImage("platform"), 0.5f, 0.55f, 0.1f, 0.04f));
        //level 2
        level2Platforms = new ArrayList<>();
        level2Platforms.add(new RectangleImage(GameImage.getImage("platform"), 0.4f, 0.7f, 0.2f, 0.1f));
        level2Platforms.add(new RectangleImage(GameImage.getImage("platform"), 0.7f, 0.7f, 0.2f, 0.1f));
        level2Platforms.add(new RectangleImage(GameImage.getImage("platform"), 1.0f, 0.7f, 0.2f, 0.1f));
        //level 3
        level3Platforms = new ArrayList<>();
        //level 4
        level4Platforms = new ArrayList<>();
        //level 5
        level5Platforms = new ArrayList<>();
        //level 6
        level6Platforms = new ArrayList<>();
        //level 7
        level7Platforms = new ArrayList<>();
        //level 8
        level8Platforms = new ArrayList<>();
        //level 9
        level9Platforms = new ArrayList<>();
    }

    public void setxVel(float xVel) {
        platformxVel = (float) (Math.round(xVel * screenWidth));
    }

    public float getxVel() {
        return platformxVel;
    }

    public void renderPlatforms(Graphics g, ActionListener imageObserver, LevelStateManager levelState) {
        Graphics2D g2 = (Graphics2D) g;
        for (RectangleImage platforms : getPlatformLevels(levelState)) {
            platforms.draw(g2, imageObserver);
        }
    }

    public void update(LevelStateManager levelState) {
        for (RectangleImage platforms : getPlatformLevels(levelState)) {
            platforms.getRectangle().x += platformxVel;
        }
    }

    public ArrayList<RectangleImage> getPlatformLevels(LevelStateManager levelState) {
        ArrayList<RectangleImage> stagePlatforms = null;
        switch (levelState.getState()) {
            case LEVEL_1:
                stagePlatforms = level1Platforms;
                break;
            case LEVEL_2:
                stagePlatforms = level2Platforms;
                break;
            case LEVEL_3:
                stagePlatforms = level3Platforms;
                break;
            case LEVEL_4:
                stagePlatforms = level4Platforms;
                break;
            case LEVEL_5:
                stagePlatforms = level5Platforms;
                break;
            case LEVEL_6:
                stagePlatforms = level6Platforms;
                break;
            case LEVEL_7:
                stagePlatforms = level7Platforms;
                break;
            case LEVEL_8:
                stagePlatforms = level8Platforms;
                break;
            case LEVEL_9:
                stagePlatforms = level9Platforms;
                break;
        }
        return stagePlatforms;
    }

    public void relocatePlatforms() {
        for (RectangleImage platforms : level1Platforms) {
            platforms.getRectangle().x = (int) platforms.getX();
        }
        for (RectangleImage platforms : level2Platforms) {
            platforms.getRectangle().x = (int) platforms.getX();
        }
        for (RectangleImage platforms : level3Platforms) {
            platforms.getRectangle().x = (int) platforms.getX();
        }
        for (RectangleImage platforms : level4Platforms) {
            platforms.getRectangle().x = (int) platforms.getX();
        }
        for (RectangleImage platforms : level5Platforms) {
            platforms.getRectangle().x = (int) platforms.getX();
        }
        for (RectangleImage platforms : level6Platforms) {
            platforms.getRectangle().x = (int) platforms.getX();
        }
        for (RectangleImage platforms : level7Platforms) {
            platforms.getRectangle().x = (int) platforms.getX();
        }
        for (RectangleImage platforms : level8Platforms) {
            platforms.getRectangle().x = (int) platforms.getX();
        }
        for (RectangleImage platforms : level9Platforms) {
            platforms.getRectangle().x = (int) platforms.getX();
        }
    }

    public void savePlatforms(LevelStateManager levelState) {
        File outputFile;
        BufferedWriter outputWriter;

        try {
            outputFile = new File("save platforms.txt");
            outputWriter = new BufferedWriter(new FileWriter(outputFile));

            for (RectangleImage platforms : getPlatformLevels(levelState)) {
                outputWriter.write(Double.toString(platforms.getRectangle().x / screenWidth) + "\n");
            }

            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPlatforms(LevelStateManager levelState) {
        File inputFile;
        BufferedReader inputReader;

        try {
            inputFile = new File("save platforms.txt");
            inputReader = new BufferedReader(new FileReader(inputFile));

            for (RectangleImage platforms : getPlatformLevels(levelState)) {
                platforms.getRectangle().x = (int) (Double.parseDouble(inputReader.readLine()) * screenWidth);
            }

            inputReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RectangleImage> getLevel1Platforms() {
        return level1Platforms;
    }
}
