import java.awt.*;

class Enemy {
    int x, y;
    int speed;
    int size;
    int health;
    int type;
    //integers for enemy characteristics

    Rectangle hitbox;// hitbox for enemies

    Enemy(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        //different types of enemies

        if (type == 0) { // Normal enemy
            speed = 2;
            size = 40;
            health = 1;
        } else if (type == 1) { // Fast enemy
            speed = 3;
            size = 30;
            health = 1;
        } else { // Tank enemy, or heavy
            speed = 1;
            size = 60;
            health = 3;
        }

        hitbox = new Rectangle(x, y, size, size);
    }

    void moveToward(Player p) {
        if (p.x > x) x += speed;
        if (p.x < x) x -= speed;
        if (p.y > y) y += speed;
        if (p.y < y) y -= speed;

        hitbox.setLocation(x, y);
        //ensures that the enemies move towards the player and that their hitboxes follow
    }

    void resetPosition() {
        x = (int)(Math.random() * 1000);
        y = (int)(Math.random() * 700);
        hitbox.setLocation(x, y);
        //the enemies spawn at a random location on the allowed grid after a new wave starts
    }

    void draw(Graphics2D g) {
        if (type == 0) g.setColor(Color.RED);
        else if (type == 1) g.setColor(Color.ORANGE);
        else g.setColor(Color.MAGENTA);

        g.fillRect(x, y, size, size);
    }
}

