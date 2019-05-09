package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Save {
    private Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    public BufferedWriter outputWriter;
    public File outputFile;
    private double screenWidth = resolution.width;
    private double screenHeight = resolution.height;
    static double playerX;
    static double playerY;

    public static double[] saveInformation = {playerY, playerY};

    public static int pXLoc = 0;
    public static int pYLoc = 1;

    private void fileCheck(String pathName) {
        File file = new File(pathName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkFileExistance() {
        fileCheck("save player.txt");
        fileCheck("save platforms.txt");
        fileCheck("save enemy bots.txt");
        fileCheck("save background.txt");
        fileCheck("save level state.txt");
    }

    public void setPlayerLocation(int x, int y) {
        playerX = x;
        playerY = y;
    }

    public void saveFile(String filePath) {
        saveInformation = new double[]{playerX / screenWidth, playerY / screenHeight};
        try {
            outputFile = new File(filePath);
            outputWriter = new BufferedWriter(new FileWriter(outputFile));

            for (int i = 0; i < saveInformation.length; i++) {
                outputWriter.write(Double.toString(saveInformation[i]) + "\n");
            }

            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFile(String filePath) {
        File inputFile;
        BufferedReader inputReader;

        try {
            inputFile = new File(filePath);
            inputReader = new BufferedReader(new FileReader(inputFile));

            for (int i = 0; i < saveInformation.length; i++) {
                saveInformation[i] = Double.parseDouble(inputReader.readLine());
            }

            inputReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
