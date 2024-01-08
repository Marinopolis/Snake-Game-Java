import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Snake {
    private static final int INITIAL_BODY_PARTS = 6;

    private final int[] x;
    private final int[] y;
    private int bodyParts;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private Direction direction;
    private final Random random;

    public Snake() {
        x = new int[GameConstants.GAME_UNITS];
        y = new int[GameConstants.GAME_UNITS];
        direction = Direction.RIGHT;
        random = new Random();
        reset();
    }

    private void reset() {
        bodyParts = INITIAL_BODY_PARTS;
        applesEaten = 0;
        newApple();
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case UP -> y[0] -= GameConstants.UNIT_SIZE;
            case DOWN -> y[0] += GameConstants.UNIT_SIZE;
            case LEFT -> x[0] -= GameConstants.UNIT_SIZE;
            case RIGHT -> x[0] += GameConstants.UNIT_SIZE;
        }
    }

    public void eatApple() {
        bodyParts++;
        applesEaten++;
        newApple();
    }

    public boolean isWithinBounds(int screenWidth, int screenHeight) {
        return x[0] >= 0 && x[0] < screenWidth && y[0] >= 0 && y[0] < screenHeight;
    }

    public boolean isCollidingWithItself() {
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean headTouchesApple() {
        return x[0] == appleX && y[0] == appleY;
    }

    private void newApple() {
        appleX = GameConstants.UNIT_SIZE * random.nextInt(GameConstants.SCREEN_WIDTH / GameConstants.UNIT_SIZE);
        appleY = GameConstants.UNIT_SIZE * random.nextInt(GameConstants.SCREEN_HEIGHT / GameConstants.UNIT_SIZE);
    }

    public void processKeyInput(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> direction = Direction.LEFT;
            case KeyEvent.VK_RIGHT -> direction = Direction.RIGHT;
            case KeyEvent.VK_UP -> direction = Direction.UP;
            case KeyEvent.VK_DOWN -> direction = Direction.DOWN;
        }
    }

    public int getHeadX() {
        return x[0];
    }

    public int getHeadY() {
        return y[0];
    }

    public int getAppleX() {
        return appleX;
    }

    public int getAppleY() {
        return appleY;
    }

    public int getApplesEaten() {
        return applesEaten;
    }

    public int getX(int index) {
        return x[index];
    }

    public int getY(int index) {
        return y[index];
    }

    public int getBodyParts() {
        return bodyParts;
    }

    public boolean isHeadAtCorner() {
        return (x[0] == 0 && y[0] == 0) ||      // Top-left corner
                (x[0] == 0 && y[0] == GameConstants.SCREEN_HEIGHT - GameConstants.UNIT_SIZE) ||  // Bottom-left corner
                (x[0] == GameConstants.SCREEN_WIDTH - GameConstants.UNIT_SIZE && y[0] == 0) ||  // Top-right corner
                (x[0] == GameConstants.SCREEN_WIDTH - GameConstants.UNIT_SIZE && y[0] == GameConstants.SCREEN_HEIGHT - GameConstants.UNIT_SIZE);  // Bottom-right corner
    }
}
