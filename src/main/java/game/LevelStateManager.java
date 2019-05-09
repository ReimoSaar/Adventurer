package game;

import java.io.*;

public class LevelStateManager {
    private static LevelState state = LevelState.LEVEL_1;
    private int levelStateNumber;

    public void setState(LevelState state) {
        this.state = state;
    }

    public LevelState getState() {
        return state;
    }

    public void saveLevelState() {
        File outputFile;
        BufferedWriter outputWriter;
        levelStateNumberValue();

        try {
            outputFile = new File("save level state.txt");
            outputWriter = new BufferedWriter(new FileWriter(outputFile));

            outputWriter.write(Integer.toString(levelStateNumber));

            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadLevelState() {
        File inputFile;
        BufferedReader inputReader;

        try {
            inputFile = new File("save level state.txt");
            inputReader = new BufferedReader(new FileReader(inputFile));

                setLevelStateNumberValue(Integer.parseInt(inputReader.readLine()));

            inputReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void levelStateNumberValue() {
        if (getState() == LevelState.LEVEL_1) {
            levelStateNumber = 1;
        }
        if (getState() == LevelState.LEVEL_2) {
            levelStateNumber = 2;
        }
        if (getState() == LevelState.LEVEL_3) {
            levelStateNumber = 3;
        }
        if (getState() == LevelState.LEVEL_4) {
            levelStateNumber = 4;
        }
        if (getState() == LevelState.LEVEL_5) {
            levelStateNumber = 5;
        }
        if (getState() == LevelState.LEVEL_6) {
            levelStateNumber = 6;
        }
        if (getState() == LevelState.LEVEL_7) {
            levelStateNumber = 7;
        }
        if (getState() == LevelState.LEVEL_8) {
            levelStateNumber = 8;
        }
        if (getState() == LevelState.LEVEL_9) {
            levelStateNumber = 9;
        }
    }

    public void setLevelStateNumberValue(int i) {
        if (i == 1) {
            setState(LevelState.LEVEL_1);
        }
        if (i == 2) {
            setState(LevelState.LEVEL_2);
        }
        if (i == 3) {
            setState(LevelState.LEVEL_3);
        }
        if (i == 4) {
            setState(LevelState.LEVEL_4);
        }
        if (i == 5) {
            setState(LevelState.LEVEL_5);
        }
        if (i == 6) {
            setState(LevelState.LEVEL_6);
        }
        if (i == 7) {
            setState(LevelState.LEVEL_7);
        }
        if (i == 8) {
            setState(LevelState.LEVEL_8);
        }
        if (i == 9) {
            setState(LevelState.LEVEL_9);
        }
    }
}
