import java.awt.*;

class Bullet {
    int x, y;
    int dx, dy;
    Rectangle hitbox; // hitbox for bullet

    Bullet(int startX, int startY, int targetX, int targetY) {
        x = startX;
        y = startY;

        dx = (targetX - startX) / 10;
        dy = (targetY - startY) / 10;

        hitbox = new Rectangle(x, y, 10, 10);
    }

    void move() {
        x += dx;
        y += dy;
        hitbox.setLocation(x, y);
        //directions for how the bullet moves
    }

    void draw(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, 10, 10);
        //icon for how the bullet looks
    }

    public boolean offScreen(int width, int height) {
        return x < 0 || x > width || y < 0 || y > height;
    }



}

