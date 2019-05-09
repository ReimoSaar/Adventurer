package game;

public class Key {
    public static Key right = new Key();
    public static Key left = new Key();
    public static Key jump = new Key();

    public boolean isDown;

    public void toggle(){
        isDown = !isDown;
    }
}
