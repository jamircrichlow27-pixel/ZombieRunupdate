import java.awt.*;

class Player {
    int x, y;
    int speed = 5;
    int health = 100;
    Rectangle hitbox;

    Player(int x, int y) {
        this.x = x;
        this.y = y;
        hitbox = new Rectangle(x, y, 40, 40);
    }

    void move(boolean up, boolean down, boolean left, boolean right) {
        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;

        hitbox.setLocation(x, y);
    }

    void knockbackFrom(Enemy e) {
        if (e.x < x) x += 30;
        else x -= 30;

        if (e.y < y) y += 30;
        else y -= 30;

        hitbox.setLocation(x, y);
    }

    void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 40, 40);
    }
}

