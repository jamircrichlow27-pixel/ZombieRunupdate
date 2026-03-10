import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class ZombieRun implements Runnable, KeyListener, MouseListener {

    final int WIDTH = 1000; // width of pixels on screen
    final int HEIGHT = 700; // height of pixels on screen

    JFrame frame;
    Canvas canvas; 
    BufferStrategy bufferStrategy;

    Player player;
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();

    boolean up, down, left, right;
    boolean gameOver = false;

    int wave = 1;

    Rectangle restartButton = new Rectangle(400, 400, 200, 60); // size of restart button

    public ZombieRun() {
        setUpGraphics();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        startGame();
    }

    void startGame() {
        player = new Player(500, 350);
        bullets.clear();
        enemies.clear();
        wave = 1;
        gameOver = false;
        spawnWave();
    }

    public static void main(String[] args) {
        ZombieRun game = new ZombieRun();
        new Thread(game).start();
    }

    public void run() {
        while (true) {
            moveThings();
            render();
            pause(16);
        }
    }

    public void moveThings() {
        if (gameOver) return; player.move(up, down, left, right);

        // bullets
        for (int i = 0; i < bullets.size(); i++) { bullets.get(i).move();
            if (bullets.get(i).offScreen(WIDTH, HEIGHT)) {
                bullets.remove(i);
                i--;
            }
        }

        // enemies
        for (int i = 0; i < enemies.size(); i++) {Enemy e = enemies.get(i);
            e.moveToward(player);

            // player is hit
            if (e.hitbox.intersects(player.hitbox)) {player.health -= 10;player.knockbackFrom(e);
                e.resetPosition();

                if (player.health <= 0) {gameOver = true;
                }
            }
        }

        // bullet vs the enemy
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                if (bullets.get(i).hitbox.intersects(enemies.get(j).hitbox)) {enemies.get(j).health--;
                    bullets.remove(i);

                    if (enemies.get(j).health <= 0) {enemies.remove(j);
                    }
                    return;
                }
            }
        }

        if (enemies.size() == 0) {wave++;
            spawnWave();
        }
    }

    void spawnWave() {
        for (int i = 0; i < wave + 2; i++) {
            int type = (int)(Math.random() * 3); // 0,1,2
             enemies.add(new Enemy(
                    (int)(Math.random() * WIDTH),
                    (int)(Math.random() * HEIGHT),
                    type
            ));
        }
    }

    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 350, 250);

            g.setFont(new Font("Arial", Font.PLAIN, 25));
            g.drawString("Wave Reached: " + wave, 400, 300);

            // restart button
            g.setColor(Color.GRAY);
            g.fillRect(restartButton.x, restartButton.y,
                    restartButton.width, restartButton.height);

            g.setColor(Color.BLACK);
            g.drawString("RESTART", 450, 440);

        } else {

            player.draw(g);
            for (Bullet b : bullets) b.draw(g);
            for (Enemy e : enemies) e.draw(g);

            g.setColor(Color.WHITE);
            g.drawString("Health: " + player.health, 20, 20);
            g.drawString("Wave: " + wave, 20, 40);
        }

        g.dispose();
        bufferStrategy.show();
    }

    public void pause(int time) {
        try { Thread.sleep(time); } catch (Exception e) {}
    }

    private void setUpGraphics() {
        frame = new JFrame("Arena Shooter");
        canvas = new Canvas();
        canvas.setSize(WIDTH, HEIGHT);

        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
    }


    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) up = true;
        if (e.getKeyCode() == KeyEvent.VK_S) down = true;
        if (e.getKeyCode() == KeyEvent.VK_A) left = true;
        if (e.getKeyCode() == KeyEvent.VK_D) right = true; // keys pressed for movement
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) up = false;
        if (e.getKeyCode() == KeyEvent.VK_S) down = false;
        if (e.getKeyCode() == KeyEvent.VK_A) left = false;
        if (e.getKeyCode() == KeyEvent.VK_D) right = false; // keys released for movement
    }

    public void keyTyped(KeyEvent e) {}

    public void mousePressed(MouseEvent e) {
        if (gameOver) {
            if (restartButton.contains(e.getX(), e.getY())) {
                startGame();
            }
        } else {
            bullets.add(new Bullet(player.x + 20, player.y + 20, e.getX(), e.getY()));
        }
    }// click render for restart button and bullets

    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}