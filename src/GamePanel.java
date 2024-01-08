import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {
    private final Timer timer;
    private final Snake snake;

    public GamePanel() {
        snake = new Snake();
        setPreferredSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        timer = new Timer(GameConstants.DELAY, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        snake.move();
        checkApple();
        checkCollisions();
        repaint();
    }

    private void checkApple() {
        if (snake.headTouchesApple()) {
            snake.eatApple();
        }
    }

    private void checkCollisions() {
        if (!snake.isWithinBounds(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT) ||
                snake.isCollidingWithItself() || snake.isHeadAtCorner()) {
            gameOver();
        }
    }

    private void gameOver() {
        timer.stop();
        repaint();
        displayGameOverMessage();
    }

    private void displayGameOverMessage() {
        Font font = new Font("Ink Free", Font.BOLD, 40);
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.buttonFont", font);

        JLabel message = new JLabel("Game Over! Your Score: " + snake.getApplesEaten());
        message.setFont(font);
        message.setForeground(Color.red);

        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        drawBorders(g);

        g.setColor(Color.RED);
        g.fillOval(snake.getAppleX(), snake.getAppleY(), GameConstants.UNIT_SIZE, GameConstants.UNIT_SIZE);

        drawSnake(g);

        drawScore(g);
    }

    private void drawBorders(Graphics g) {
        g.setColor(Color.WHITE);
        for (int i = 0; i < GameConstants.SCREEN_WIDTH / GameConstants.UNIT_SIZE; i++) {
            g.drawLine(i * GameConstants.UNIT_SIZE, 0, i * GameConstants.UNIT_SIZE, GameConstants.SCREEN_HEIGHT);
            g.drawLine(0, i * GameConstants.UNIT_SIZE, GameConstants.SCREEN_WIDTH, i * GameConstants.UNIT_SIZE);
        }
    }

    private void drawSnake(Graphics g) {
        for (int i = 0; i < snake.getBodyParts(); i++) {
            g.setColor(i == 0 ? Color.GREEN : new Color(45, 180, 0));
            g.fillRect(snake.getX(i), snake.getY(i), GameConstants.UNIT_SIZE, GameConstants.UNIT_SIZE);
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + snake.getApplesEaten(), (GameConstants.SCREEN_WIDTH - metrics.stringWidth("Score: " + snake.getApplesEaten())) / 2, g.getFont().getSize());
    }

    private class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            snake.processKeyInput(e.getKeyCode());
        }
    }


}
