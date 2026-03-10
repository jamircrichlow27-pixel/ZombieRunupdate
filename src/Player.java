import java.awt.*;

class Player {
    int x, y;
    int speed = 7; // how fast my player can move
    int health = 100; // how much health player has
    Rectangle hitbox; // hitbox to render where the player is

    Player(int x, int y) {
        this.x = x;
        this.y = y;
        hitbox = new Rectangle(x, y, 40, 40); // size of hitbox
    }

    void move(boolean up, boolean down, boolean left, boolean right) {
        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;
        // direction player moves

        hitbox.setLocation(x, y); // ensures hitbox stays with player
    }

    void knockbackFrom(Enemy e) {
        if (e.x < x) x += 30;
        else x -= 30;

        if (e.y < y) y += 30;
        else y -= 30;

        hitbox.setLocation(x, y);
        // when player gets hit by enemy, player gets pushed back and hitbox goes with
    }

    void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 40, 40); // icon for player
    }
}

