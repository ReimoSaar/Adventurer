package game.characters;

import game.BufferedImageLoader;
import game.Bullet;
import game.RectangleImage;

import java.awt.*;
import java.util.ArrayList;

public class EnemyBotFeatures {

    private Character player;

    public EnemyBotFeatures(Character player) {
        this.player = player;
    }

    public void addShooting(Character enemyBot, String path, ArrayList<Bullet> bullets, float width, float height, float xVel, float yVel, boolean friendly) {
        if (enemyBot.getXVel() > 0) {
            enemyBot.setFacingLeft(false);
        }
        if (enemyBot.getXVel() < 0) {
            enemyBot.setFacingLeft(true);
        }
        enemyBot.incrementShootTime();
        if (enemyBot.getShootTime() > 100) {
            if (!canShoot(enemyBot)) {
                enemyBot.setShootTime(100);
            } else {
                enemyBot.setShootTime(0);
            }
        }
        Rectangle rectangle = enemyBot.getRectangle();
        if (enemyBot.getShootTime() == 0) {
            enemyBot.getShootingSound().play();
            if (enemyBot.isFacingLeft()) {
                bullets.add(new Bullet(BufferedImageLoader.loadBufferedImage(path), rectangle.x,
                        rectangle.y + rectangle.height / 3.0f, width, height, -xVel, yVel, friendly));
            }
            if (!enemyBot.isFacingLeft()) {
                bullets.add(new Bullet(BufferedImageLoader.loadBufferedImage(path), rectangle.x + rectangle.width,
                        rectangle.y + rectangle.height / 3.0f, width, height, xVel, yVel, friendly));
            }
        }
    }

    private boolean canShoot(Character enemyBot) {
        Rectangle rectangle = enemyBot.getRectangle();
        if (enemyBot.isFacingLeft() && player.getRectangle().x < rectangle.x ||
                !enemyBot.isFacingLeft() && player.getRectangle().x > rectangle.x) {
            if (rectangle.y + rectangle.height - 2 * rectangle.height / 3 >= player.getRectangle().y
                    && rectangle.y + rectangle.height / 3 <= player.getRectangle().y + player.getRectangle().height) {
                return true;
            }
        }
        return false;
    }
}
