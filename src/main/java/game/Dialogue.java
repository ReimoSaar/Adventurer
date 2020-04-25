package game;

import game.characters.Character;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Dialogue {
    private static ArrayList<Checkpoint> dialogueLocations;
    private RectangleImage dialogueBackground;
    private boolean canRemove = false;
    private int counter = 0;
    private Game game;
    private Character player;


    public Dialogue(Game game) {
        dialogueBackground = new RectangleImage(GameImage.getImage("dialogue_box"), 0.0f, 0.8f, 1.0f, 0.2f);
        dialogueLocations = new ArrayList<>();
        this.game = game;
        this.player = game.getPlayer();
    }

    public void renderDialogue(Graphics g, ImageObserver imageObserver) {
        Graphics2D g2 = (Graphics2D) g;
        dialogueBackground.draw(g2, imageObserver);
        addText(g, imageObserver);
    }

    public void addLevel1Dialogues() {
        dialogueLocations.add(new Checkpoint(0.9f, "dialogue1", 0, GameImage.getImage("player_dialogue_image")));
        dialogueLocations.add(new Checkpoint(0.9f, "dialogue2", 1, GameImage.getImage("enemy_dialogue_image")));
        dialogueLocations.add(new Checkpoint(0.9f, "dialogue3", 2, GameImage.getImage("enemy_dialogue_image")));
        dialogueLocations.add(new Checkpoint(1.4f, "dialogue4", 0, GameImage.getImage("player_dialogue_image")));
        dialogueLocations.add(new Checkpoint(1.4f, "dialogue5", 1, GameImage.getImage("enemy_dialogue_image")));
        dialogueLocations.add(new Checkpoint(1.4f, "dialogue6", 2, GameImage.getImage("player_dialogue_image")));
        dialogueLocations.add(new Checkpoint(1.4f, "dialogue7", 3, GameImage.getImage("player_dialogue_image")));
        dialogueLocations.add(new Checkpoint(1.9f, "dialogue8", 0, GameImage.getImage("enemy_dialogue_image")));
    }

    public void addDialogueInteraction() {
        if (canRemove) {
            dialogueLocations.removeIf(dialogueCheck -> game.getPlayer().getRectangle().x >= dialogueCheck.rectangle.x);
        }

        if (hasPassedDialogueCheck()) {
            game.getGameState().setState(GameState.IN_DIALOGUE);
        }

        for (Checkpoint checkPoint : dialogueLocations) {
            checkPoint.rectangle.x += game.getPlatform().getxVel();
        }
    }

    public void prepareDialogue() {
        dialogueLocations.clear();
    }

    private void addText(Graphics g, ImageObserver imageObserver) {
        for (Checkpoint checkPoint : dialogueLocations) {
            if (checkPoint.turn == counter) {
                checkPoint.addText(g, imageObserver);
            }
        }
    }

    public void update() {
        addDialogueInteraction();
    }

    public boolean hasPassedDialogueCheck() {
        for (Checkpoint checkPoint : dialogueLocations) {
            if (player.getRectangle().x >= checkPoint.rectangle.x) {
                return true;
            }
        }
        return false;
    }

    public void mousePressed(GameStateManager gameState) {
        Checkpoint check = null;
        for (Checkpoint checkPoint : dialogueLocations) {
            if (player.getRectangle().x >= checkPoint.rectangle.x) {
                check = checkPoint;
            }
        }
        if (check != null) {
            if (counter < check.turn) {
                if (gameState.getState() == GameState.IN_DIALOGUE) {
                    counter++;
                }
            } else {
                counter = 0;
                canRemove = true;
                gameState.setState(GameState.IN_GAME);
            }
        }
    }

    private class Checkpoint {
        private int screenWidth;
        private int screenHeight;
        private Rectangle rectangle;
        private String string;
        private int turn;
        private Image image;

        private Checkpoint(float x, String string, int turn, Image image) {
            Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
            x = x * resolution.width;
            this.string = string;
            this.turn = turn;
            this.rectangle = new Rectangle((int) x, 0, 0, 0);
            this.image = image;
            screenWidth = resolution.width;
            screenHeight = resolution.height;
        }

        private void addText(Graphics g, ImageObserver imageObserver) {
            Graphics2D g2 = (Graphics2D) g;
            if (rectangle.x <= player.getRectangle().x) {
                new Text(g, string, 0.1f, 0.9f, 20, "arial", Color.BLACK, Font.BOLD);
                g2.drawImage(image, (int) (screenWidth * 0.455f), (int) (screenHeight * 0.72f), (int) (screenWidth * 0.09f), (int) (screenHeight * 0.097f), (ImageObserver) imageObserver);
            }
        }
    }
}
