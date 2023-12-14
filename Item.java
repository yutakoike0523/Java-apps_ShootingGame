import java.awt.*;

class Item extends MovingObject {
    Item() {
        w = h = 10;  // Adjust the size as needed
        dy = 2;      // Adjust the speed as needed
        hp = 0;
    }

    void move(Graphics buf, int apWidth, int apHeight) {
        if (hp > 0) {
            buf.setColor(Color.YELLOW);  // Set the color for the item
            buf.fillOval(x - w, y - h, 2 * w, 2 * h);
            if (y > 0 && y < apHeight && x > 0 && x < apWidth)
                y = y + dy;
            else
                hp = 0;
        }
    }

    void revive(int x, int y,int dx,int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        hp = 1;
    }
}
