import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Dialogue extends JPanel implements MouseListener, ActionListener {
    GameStateManager gameState;
    private static ArrayList<Checkpoint> dialogueLocations;
    private ArrayList<Checkpoint> passedDialogueLocations;
    private RectangleImage dialogueBackground;
    private Platform platform;
    private boolean canRemove = false;
    private int counter = 0;


    public Dialogue() {
        dialogueBackground = new RectangleImage(GameImage.getImage("dialogue_box"), 0.0f, 0.8f, 1.0f, 0.2f);
        platform = new Platform();
        gameState = new GameStateManager();
        dialogueLocations = new ArrayList<>();
    }

    public void renderDialogue(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        dialogueBackground.draw(g2, this);
        addText(g);
    }

    public void addLevel1Dialogues() {
        dialogueLocations.add(new Checkpoint(0.9f, "Reimo", 0, GameImage.getImage("player_dialogue_image")));
        dialogueLocations.add(new Checkpoint(0.9f, "wddw", 1, GameImage.getImage("enemy_dialogue_image")));
        dialogueLocations.add(new Checkpoint(0.9f, "wdddsadaw", 2, GameImage.getImage("enemy_dialogue_image")));
        dialogueLocations.add(new Checkpoint(1.4f, "XD", 0, GameImage.getImage("player_dialogue_image")));
        dialogueLocations.add(new Checkpoint(1.4f, "LOL", 1, GameImage.getImage("enemy_dialogue_image")));
        dialogueLocations.add(new Checkpoint(1.4f, "wddw", 2, GameImage.getImage("player_dialogue_image")));
        dialogueLocations.add(new Checkpoint(1.4f, "wdddsadaw", 3, GameImage.getImage("player_dialogue_image")));
        dialogueLocations.add(new Checkpoint(1.9f, "nice!", 0, GameImage.getImage("enemy_dialogue_image")));
    }

    public void addDialogueInteraction() {
        if (canRemove) {
            dialogueLocations.removeIf(dialogueCheck -> Player.getPlayer().getRectangle().x >= dialogueCheck.rectangle.x);
        }

        if (hasPassedDialogueCheck()) {
            gameState.setState(GameState.IN_DIALOGUE);
        }

        for (Checkpoint checkPoint : dialogueLocations) {
            checkPoint.rectangle.x += platform.getxVel();
        }
    }

    public void prepareDialogue() {
        dialogueLocations.removeAll(dialogueLocations);
    }

    private void addText(Graphics g) {
        for (Checkpoint checkPoint : dialogueLocations) {
            if (checkPoint.turn == counter) {
                checkPoint.addText(g, this);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        addDialogueInteraction();
    }

    public boolean hasPassedDialogueCheck() {
        for (Checkpoint checkPoint : dialogueLocations) {
            if (Player.getPlayer().getRectangle().x >= checkPoint.rectangle.x) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Checkpoint check = null;
        for (Checkpoint checkPoint : dialogueLocations) {
            if (Player.getPlayer().getRectangle().x >= checkPoint.rectangle.x) {
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

    @Override
    public void mouseReleased(MouseEvent e) {
        canRemove = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

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

        private void addText(Graphics g, ActionListener imageObserver) {
            Graphics2D g2 = (Graphics2D) g;
            if (rectangle.x <= Player.getPlayer().getRectangle().x) {
                new Text(g, string, 0.1f, 0.9f, 20, "arial", Color.BLACK, Font.BOLD);
                g2.drawImage(image, (int) (screenWidth * 0.455f), (int) (screenHeight * 0.72f), (int) (screenWidth * 0.09f), (int) (screenHeight * 0.097f), (ImageObserver) imageObserver);
            }
        }
    }
}
