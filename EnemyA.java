import java.awt.*;

class EnemyA extends MovingObject {
    EnemyA(int apWidth, int apHeight) {
        super(apWidth, apHeight);
        w = 12;
        h = 12;
        hp = 0;
    }

    void move(Graphics buf, int apWidth, int apHeight) {
        buf.setColor(Color.white);
        if (hp > 0) {
            buf.drawOval(x - w, y - h, 2 * w, 2 * h);
            x = x + dx;
            y = y + dy;
            if (y > apHeight + h)
                hp = 0;
        }
    }

    void revive(int apWidth, int apHeight,int dx,int dy) {
        x = (int) (Math.random() * (apWidth - 2 * w) + w);
        y = -h;
        dy = 1;
        if (x < apWidth / 2)
            dx = (int) (Math.random() * 2);
        else
            dx = -(int) (Math.random() * 2);
        hp = 1;
    }
}
