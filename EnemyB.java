import java.awt.*;

class EnemyB extends MovingObject {
    EnemyB(int apWidth, int apHeight) {
        super(apWidth, apHeight);
        w = 12;
        h = 12;
        hp = 10;
    }

    void move(Graphics buf, int apWidth, int apHeight) {
        buf.setColor(Color.black);
        if (hp > 0) {
            buf.drawOval(x - w, y - h, 2 * w, 2 * h);
            x = x + dx;
            y = y + dy;

            if (y > apHeight + h || y < 0)
                dy = -dy;
            else if (x > apWidth + w || x < 0)
                dx = -dx;
        }
    }

    void revive(int apWidth, int apHeight,int dx,int dy) {
        x = (int) (Math.random() * (apWidth - 2 * w) + w);
        y = (int) (Math.random() * (apWidth - 2 * w) + w);
        this.dx = dx;
        this.dy = dy;
        if (x < apWidth)
            dx = (int) (Math.random() * 2);
        else
            dx = -(int) (Math.random() * 2);
        hp = 10;
    }
}