import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private class Tile {
        int x, y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int width, height;
    int tileSize = 25;
    Tile snakeHead;
    Tile food;
    Random random;
    Timer gameLoop;
    int velocityX, velocityY;
    ArrayList<Tile> snakeBody;

    public SnakeGame(int width, int height) {
        this.width = width;
        this.height = height;

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        random = new Random();
        snakeHead = new Tile(5, 5);
        food = new Tile(10, 10);
        snakeBody = new ArrayList<>();

        placeFood();

        velocityX = 0;
        velocityY = 1;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Grid
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < height / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, height);
            g.drawLine(0, i * tileSize, width, i * tileSize);
        }

        // Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + snakeBody.size(), 10, 20);

        // Food
        g.setColor(Color.RED);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

        // Snake Head
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        // Snake Body
        for (Tile part : snakeBody) {
            g.fillRect(part.x * tileSize, part.y * tileSize, tileSize, tileSize);
        }
    }

    public void placeFood() {
        boolean onSnake;
        do {
            onSnake = false;
            food.x = random.nextInt(width / tileSize);
            food.y = random.nextInt(height / tileSize);
            if (collision(food, snakeHead)) {
                onSnake = true;
                continue;
            }
            for (Tile part : snakeBody) {
                if (collision(food, part)) {
                    onSnake = true;
                    break;
                }
            }
        } while (onSnake);
    }

    public boolean collision(Tile a, Tile b) {
        return a.x == b.x && a.y == b.y;
    }

    public void move() {
        // Move body first
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
        if (!snakeBody.isEmpty()) {
            snakeBody.get(0).x = snakeHead.x;
            snakeBody.get(0).y = snakeHead.y;
        }

        // Move head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Wall collision
        if (snakeHead.x < 0 || snakeHead.x >= width / tileSize || snakeHead.y < 0 || snakeHead.y >= height / tileSize) {
            gameLoop.stop();
            JOptionPane.showMessageDialog(this, "Game Over! You hit the wall.");
            return;
        }

        // Self collision
        for (Tile part : snakeBody) {
            if (collision(snakeHead, part)) {
                gameLoop.stop();
                JOptionPane.showMessageDialog(this, "Game Over! You hit yourself.");
                return;
            }
        }

        // Food collision
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(snakeHead.x, snakeHead.y)); // Add body at current head
            placeFood();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (code == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (code == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (code == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
